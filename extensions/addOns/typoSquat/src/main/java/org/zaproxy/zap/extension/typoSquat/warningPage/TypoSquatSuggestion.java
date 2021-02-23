package org.zaproxy.zap.extension.typoSquat.warningPage;

import java.util.UUID;

public class TypoSquatSuggestion {
    private String domain;
    private UUID uuid;

    /**
     * Create a Typosquat suggestion.
     *
     * @param   domain  The domain of the suggestion.
     */
    public TypoSquatSuggestion(String domain) {
        this.domain = domain;

        uuid = java.util.UUID.randomUUID();
    }

    /**
     * Retreive the html-content showing the suggestion details.
     *
     * @param   template    The template for the suggestion details.
     * @return  String      The template with the fields filled.
     */
    public String getHTML(String template) {
        return replaceFieldsInTemplate(template);
    }

    /**
     * Replaces the correct fields in the templates.
     *
     * @param   template    The template to substitute the values in.
     * @return  String      The template with correct fields substituted.
     */
    private String replaceFieldsInTemplate(String template) {
        return template.replaceAll(DOMAIN, domain)
                       .replaceAll(UUID, uuid.toString());
    }

    /**
     * @return  Textual representation of the domain of this suggestion.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @return  Textual representation of the UUID of this suggestion.
     */
    public String getUUID() {
        return uuid.toString();
    }

    /**
     * These are the regexes that match the fields in the HTML-page template.
     */
    private static final String DOMAIN = "\\{DOMAIN\\}";
    private static final String UUID = "\\{UUID\\}";
}
