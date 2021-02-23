# Meeting supervisor 22/11/2019

Cookie rule: all three flags need to be set.

Split Model and hooking in ZAP  
B'cause different resp. in one class now.  
Alert in separate class: can only change that class to change alerts.

Seq. diagrams are not really necessary, but might be helpful.

Ideally: this class does ., this class does .  
\+ Make sure there is a logical relationship (e.g. no triangle in the class diagram).  
-> Sequence diagram can be helpful here.

Raising alert in ZAP:  
Ok to continue this way, just need to figure out why it's not working in our system. Raising alert seems fine to assistant.

Removing policies is not requested => Don't do.

Loading policy files in windows: Get URL from file instead of path, then it should work.  
It should work...

HashMap name->policy:  
getName on each policy is better, we never use HashMap-specific functionality.

README:  
No 'windows-edition' needed.  
Can add explanation for HTTPMessage class, not really needed. Do add which specific HttpMessage class it is, where that is located.  
Location of interfaces (for imports) to be added.