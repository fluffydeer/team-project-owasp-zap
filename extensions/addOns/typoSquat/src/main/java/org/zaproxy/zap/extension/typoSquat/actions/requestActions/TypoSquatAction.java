package org.zaproxy.zap.extension.typoSquat.actions.requestActions;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.typoSquat.fileHandler.DomainHandler;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;
import org.zaproxy.zap.extension.typoSquat.actions.userActions.AbstractUserAction;
import org.zaproxy.zap.extension.typoSquat.actions.userActions.UserNoAction;
import org.zaproxy.zap.extension.typoSquat.actions.userActions.UserProceedAction;
import org.zaproxy.zap.extension.typoSquat.actions.userActions.UserRedirectAction;
import org.zaproxy.zap.extension.typoSquat.warningPage.TypoSquatSuggestion;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class TypoSquatAction extends AbstractRequestAction {
    private LinkedHashSet<String> matchedDomains;
    private UUID original_uuid;
    private UUID no_action_uuid;
    private DomainHandler domainHandler;

    public TypoSquatAction(LinkedHashSet<String> matchedDomains, DomainHandler domainHandler) {
        this.matchedDomains = matchedDomains;
        this.domainHandler = domainHandler;

        original_uuid = UUID.randomUUID();
        no_action_uuid = UUID.randomUUID();
    }

    /**
     * When a typosquat is detected, we show the html page to the user.
     *
     * @param msg
     */
    @Override
    public boolean execute(HttpMessage msg, SessionManager sessionManager) throws IOException {
        List<TypoSquatSuggestion> suggestions = createTypoSquatSuggestions();
        String originalDomain = msg.getRequestHeader().getHostName();

        String finished_page = constructWarningPage(msg, suggestions);
        addActions(suggestions, originalDomain, sessionManager);

        msg.getResponseHeader().setStatusCode(200);
        msg.getResponseBody().setBody(finished_page);
        msg.getResponseHeader().setHeader("Content-Type", "text/html; charset=utf-8");

        return true;
    }

    private List<TypoSquatSuggestion> createTypoSquatSuggestions() {
        List<TypoSquatSuggestion> suggestions = new ArrayList<>();

        for (String domain : matchedDomains) {
            suggestions.add(new TypoSquatSuggestion(domain));
        }

        return suggestions;
    }

    private String constructWarningPage(HttpMessage msg, List<TypoSquatSuggestion> suggestions) throws IOException {
        String originalDomain = msg.getRequestHeader().getHostName();

        String suggestionsString = createSuggestionsHTML(suggestions);
        String page = loadTemplate("warning-page-min.html");

        return substituteDataInPage(page, originalDomain, suggestionsString);
    }

    /**
     * Creates the HTML list of suggestions to substitute on the page.
     *
     * @return  String      The html-representation of the suggestions.
     * @throws  IOException If loading the template fails.
     */
    private String createSuggestionsHTML(List<TypoSquatSuggestion> suggestions)
            throws IOException {
        String template = loadTemplate("typosquat-suggestion-min.html");
        StringBuilder suggestionList = new StringBuilder();

        for (TypoSquatSuggestion suggestion : suggestions) {
            suggestionList.append(suggestion.getHTML(template));
        }

        return suggestionList.toString();
    }

    private void addActions(List<TypoSquatSuggestion> suggestions, String originalDomain, SessionManager sessionManager) {
        addSuggestedActions(suggestions, originalDomain, sessionManager);
        addOriginalAndNoAction(originalDomain, sessionManager);
    }

    private void addSuggestedActions(List<TypoSquatSuggestion> suggestions, String originalDomain, SessionManager sessionManager) {
        for (TypoSquatSuggestion suggestion : suggestions) {
            sessionManager.addAction(
                    new UserRedirectAction(suggestion, originalDomain, this.domainHandler)
            );
        }
    }

    private void addOriginalAndNoAction(String originalDomain, SessionManager sessionManager) {
        sessionManager.addAction(
                new UserProceedAction(original_uuid.toString(), originalDomain, domainHandler)
        );

        sessionManager.addAction(
                new UserNoAction(no_action_uuid.toString(), originalDomain)
        );
    }

    /**
     * Substitutes the variables in the template fields.
     *
     * @param   page            The page template.
     * @param   suggestionList  The html-representation of the suggestions.
     * @return  The page with correct fields replaced.
     */
    private String substituteDataInPage(String page, String originalDomain, String suggestionList) {
        return page.replaceAll(ORIGINAL_DOMAIN, originalDomain)
                .replaceAll(ORIGINAL_UUID, original_uuid.toString())
                .replaceAll(NO_ACTION_DOMAIN, originalDomain)
                .replaceAll(NO_ACTION_UUID, no_action_uuid.toString())
                .replaceAll(SUGGESTION_LIST, suggestionList);
    }

    /**
     * Loads a template from a file.
     *
     * @param   resourceName    The name of the file, relative to the resources-folder.
     * @return  String          The contents of the template-file.
     * @throws  IOException     If the template-file is not found or cannot be opened.
     */
    private String loadTemplate(String resourceName)
            throws IOException {
        InputStream in = getClass().getClassLoader().getResourceAsStream(resourceName);

        if (in == null) {
            throw new IOException("Template resource not found.");
        }

        Scanner sc = new Scanner(in);
        StringBuilder sb = new StringBuilder();

        while(sc.hasNext()){
            sb.append(sc.nextLine());
        }

        return sb.toString();
    }

    /**
     * The list of suggestions.
     */
    private final Map<String, AbstractUserAction> typoSquatSuggestions = new HashMap<>();

    /**
     * These are the regexes that match the fields in the HTML-page template.
     */
    private static final String ORIGINAL_DOMAIN = "\\{ORIGINAL_DOMAIN\\}";
    private static final String ORIGINAL_UUID = "\\{ORIGINAL_UUID\\}";
    private static final String NO_ACTION_DOMAIN = "\\{NO_ACTION_DOMAIN\\}";
    private static final String NO_ACTION_UUID = "\\{NO_ACTION_UUID\\}";
    private static final String SUGGESTION_LIST = "\\{SUGGESTION_LIST\\}";

}