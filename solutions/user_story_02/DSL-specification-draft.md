# DSL ideas

**Note:**_This is meant to be a drafting document that documents the (possibly iterative) process of coming up with the DSL specification and design surrounding it. Please don't change anything, just add an addition to the bottom of the file or make a new version with the DSL, Design and Analysis subtitles_

## Assignment

The assignment states the following:

*Instead of having to code each rule and load it with a JAR, the policy rule verifier should support
a domain-specific language (DSL).
The language should be expressive enough to support the following types of rules:*

* *Match a specific request header against a value or list of values*
* *Match a specific request header against a regex*
* *Match a specific response header against a value or list of values*
* *Match a specific response header against a regex*
* *Match the request body against a regex*
* *Match the response body against a regex*
* *Boolean operators AND, OR, NOT and support for parenthesis.*

## Interpretation

In this section I try to interpret the rules, and possibly write down what it is exactly the DSL needs to support, this is basically a transcript of my first thoughts when going through the rules. Below there is a section about my actual proposal, which might be defined in multiple substeps (e.g. proposal DSL, design, analysis + iterations of that).

*Match a specific request header against a value or list of values*  
  Matching a header: should be able to specify the rule regards a header of a request and give the specific header's name   
  Against a value or a list: Maybe `IS` and `IN` keywords for matching exact value and list-appearance  

*Match a specific request header against a regex*  
  Matching a header: same as above
  Against a regex: Maybe `MATCHING` or `LIKE` keyword (I prefer `MATCHING`, so I'll use that in the rest of the document)  

*Match a specific response header against a value or list of values*  
  Same as the first case, but now for response

*Match a specific response header against a regex*
  Same as the second case, but now for response

*Match the request body against a regex*  
  Again, maybe specify request, body and `MATCHING`

*Match the response body against a regex*  
  Idem dito, but for response

*Boolean operators AND, OR, NOT and support for parenthesis.*  
  Keywords given: `AND`, `OR` and `NOT`  
  Parenthesis `(` and `)`  
  => This is a matter of parsing the rule into some sort of execution tree. 

## Proposal
### v1
#### DSL

Here, I'll just write down some examples that can give a general idea of the way the DSL would work (not a formal description yet, that is too much work while this is all still very prone to change):
```
P:random policy name:

R:random rule name:
    NOT REQ HEAD Host IS www.google.com AND 
    (
        RES HEAD Status IN [401,403,500]
        OR
        RES BODY MATCHES [a-zA-Z0-9]*
    )

R:second rule:
    RES HEAD Status IN [200,201]
    AND
        % double line break allowed???
    NOT RES HEAD Status IN [500,422]
```

As for precedences, normal rules apply: parenthesis, not, and/or  
We ignore tabs and single line-breaks, but multiple line-breaks suggest the end of a rule.

I'm not sure if we need to support multiple rules, but that is very well possible. In that case, I suggest starting every new rule with a `R:name:` annotation. At the top of the document we can then specify `P:name:` for the policy name. New rules are started by a double line-break and the name-indication.

Maybe we can allow double line breaks as option, but when the user provides a new rule name (starting with `R:`), we end the previous rule. Although it might be way easier to just assume double linebreak as an ending 'statement' to start the next rule. In that case, you could say that a rule starts with an empty line followed by the name annotation. If we want comments I suggest to only support single-line comments and to start them with a single character (e.g. `%` or `-`) instead of a double-character annotation (like `//`). That will be easier to parse.

Within a rule, the keywords and identifiers must be separated by a space. If we ignore extra spaces, tabs and line-breaks those are also allowed, but single space is minimum. A single space or linebreak is always required (e.g. not only a tab -> probs more difficult to parse)

As a different option, we can end every 'statement' with an identifier (like `;`). This would be:

```
P:name of this cool policy;
R:name of rule:
    NOT RES BODY IS Error
    AND
    (
    REQ HEAD Status IS 200
    OR        
    REQ HEAD Status IS 302
    );
R:name of second rule:
    % Rule here
    ;
```

#### Design

**Method one:**
There would be, as is the case now, one policy and multiple rules. The policy can be created when the name is known (right at the beginning of the DSL-encoded file).  
After this, every rule can be loaded in the same way, the name is known and the body as well. These rules can be linked to the policy.  
When all rules are loaded into the policy they can be parsed into a tree-like execution structure.

**Method two:**
Again, the policy can be created at once, but the parser now continues to construct an execution-tree for each rule. Those trees are then added to the policy. The `Rule` and `Policy` have exactly the same role as they did in the first userstory. To execute. 

#### Analysis/thoughts/issues

**Method one:**
This seems pretty straightforward in general sense, specifically if we use the `;` as end-of-rule syntax. Just read 'till the semicolon and add into rule. The problem is that the rule is now responsible for parsing it's body into an executable tree. That is not a good idea. The execution-tree should be created beforehand and added to the policy in that way.

**Method two:**
The parsing of the file is more difficult in this way, but the general resulting design will probably be better. This method also seems to allow for the most re-use of code from the first userstory. After all, the parsing and DSL are the things on the assignment for userstory two.

#### More specific design

TODO
