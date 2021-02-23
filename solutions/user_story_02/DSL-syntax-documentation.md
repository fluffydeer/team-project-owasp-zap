## DSL Syntax Documentation
_Design of Software Systems: Group 3_  
_Userstory 2: Policy Language_

This document outlines the syntax of our domain specific language for the Policy Language userstory.

### Specification

#### Basics
Following are the basic requirements of the language:

* A policy file must be saved with `.txt`-extension.
* There must be exactly one policy in each file.
* There can be zero or more rules in one file, they belong to the policy.
* The name of the policy should be defined on the first line of the document.
* A line-break should be inserted directly after every regular expression.

### Example + English explanation
Following is an example of the usage of the language.

```
P:name of this cool policy;
R:name of rule:
    NOT RES BODY CONTAINS Error
    AND
    (
    RES HEAD Status IN [200,302,418]
    OR        
    RES HEAD Host IS www.google.com
    );
R:name of second rule:
    NOT REQ BODY MATCHES [a-zA-Z0-9]+
    ;
```

Start with the policy name `P:<name>;` on the first line.

Next, you must define the rules. Each rule starts with `R:<name>:`.  
*This ends in a regular colon (instead of a semicolon as with the policy) to indicate that more content should follow.*  
Everything after the `:` is the body of the rule until a `;` is reached. It is this part that should specify your rule.

The body of the rule consists of one or more checks against parts of a message, chained together by boolean operators.
  
There are multiple checks available: IS, CONTAINS, IN and MATCHES:
* `IS` expects a string-value as right argument.
* `CONTAINS` expects a string-value as right argument.
* `IN` expects a list as right argument.  
    *A list can be created by listing multiple options separated by a comma and surrounding them by square brackets (e.g. `[a,b,c]`)*
* `MATCHES` expects a regular expression as right argument.  
    *The regular expression must end with a newline (so an enter **directly** after the regex, adding extra space will result in a different regular expression).*

Each of these checks expects either a body or a header field as left argument.  
First of all we should specify if this check should act on the request or response by writing `REQ` or `RES` respectively.  
After selecting the request/response, we specify whether to take a specific field of the header or the complete body of the request/response.  
* The body can be selected by writing `BODY`.
* The field of the header can be selected by writing `HEAD <fieldname>`

This gives you four options:
* `REQ BODY`
* `REQ HEAD <fieldname>`
* `RES BODY`
* `RES HEAD <fieldname>`

An additional feature is that the name of the field is optional. This way, the user can match against the complete head of a request or response.

These checks can be chained together by using boolean operators `AND`, `OR` and `NOT`, and parenthesis.  
The precedence of operators is defined as is usual in boolean arithmetic (from high to low):
1. Parenthesis (note: round brackets `(` & `)`)
2. `NOT`
3. `AND`
4. `OR`

Note that it is not legal to insert boolean operators in the middle of a check (e.g. `RES BODY IS NOT hello` is **Illegal**).  
Using whitespace is allowed (spaces, tabs and newlines) at every point **except** in and after the regex and in string literals (the fieldname of the header and the strings in the right argument of the `IS` and `CONTAINS` checks). Spaces are allowed in a list.

End the rule with `;`. After the `;`, a next rule can immediately start, but it is recommended to start the next rule on a separate line, to keep the policy clear/clean.

Finally, save the file with `.txt`-extension and load it into the application.

If the language is specified in an incorrect way, the parser will not be able to parse it and will throw a `ParseException`. If something fails during the execution of a rule, (for example if the specified fieldname does not exist), an `EvaluationException` will be thrown.

#### Syntax (OPTIONAL)
Following is the **attempt** (attempt because we don't have a solid way to verify the completeness and correctness of this specification) to specify the complete syntax of a document, the parameters in uppercase are standard values:

```BNF
<policy-document>   ::= <opt-whitespace> "P:" <policy-name> ";" <opt-rules>
<opt-whitespace>    ::= " " <opt-whitespace> | <EOL> <opt-whitespace> | <TAB> <opt-whitespace> | ""
<policy-name>       ::= <ASCII-CHAR> <policy-name> | ""
<opt-rules>         ::= <opt-whitespace> <rules> | <opt-whitespace>
<rules>             ::= <rule> | <rule> <opt-whitespace> <rule>
<rule>              ::= "R:" <rule-name> ":" <opt-whitespace> <expr> ";"
<rule-name>         ::= <ASCII-CHAR> <rule-name> | ""

<expr>              ::= <and> | <or> | <not> | <parenthesis> | <single-expression>
<and>               ::= <expr> <opt-whitespace> " AND " <opt-whitespace> <expr>
<or>                ::= <expr> <opt-whitespace> " OR " <opt-whitespace> <expr>
<not>               ::= "NOT " <opt-whitespace> <expr>
<parenthesis>       ::= "(" <opt-whitespace> <expr> <opt-whitespace> ")"
<single-expr>       ::= <is> | <matches> | <in> | <contains>

<is>                ::= <getter> <opt-whitespace> " IS " <STRING>
<matches>           ::= <getter> <opt-whitespace> " MATCHES " <regex> <EOL>
<in>                ::= <getter> <opt-whitespace> " IN " <list>
<contains>          ::= <getter> <opt-whitespace> " CONTAINS " <STRING>

<getter>            ::= "RES " <opt-whitespace> <content> | "REQ " <opt-whitespace> <content>
<content>           ::= "BODY" | "Head " <header-field>
<list>              ::= "[" <list-values> "]"
<list-values>       ::= <STRING> | <STRING> "," <STRING> | <STRING> "," <list-values>
<regex>             ::= <ASCII-CHAR> | <ASCII-CHAR> <regex>
<header-field>      ::= <STRING>
```

where `<ASCII-CHAR>` is an ASCII character, `<EOL>` a newline character, `<TAB>` a tab character and `<STRING>` a string.