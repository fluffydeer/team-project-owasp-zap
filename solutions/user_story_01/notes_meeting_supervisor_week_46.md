
# Supervisor meeting 12/11/2019

No blocking of any requests (just alert).

Choose between html-report.\
raisealert in passivescanner is ok.\
no persistence needed. Don't take sessions into account at all.\
Using builtin alert system and report generation is good enough, just make sure it's clear which ones are our alerts.

Two methods in rules is ok.\
Splitting up is probably going to copy a lot of code.\
In case of two methods: user's own responsibility.

Similarity to passivescanner??\
Implement or use passivescanner.

Loading JAR-files must happen on runtime.\
Expect: created jar that we can load on signoff meeting.

Parallelize rules? Just keep it sequentially.

Proxy mode:\
just the default mode of ZAP

PolicyImplementation to RuleImplementation is ok\
One class per rule is ok

email-addresses: just any email in any header or body\
given list best in file, no need to change dynamically in ZAP, just in JAR

docs targeted to someone who knows java
