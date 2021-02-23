package org.zaproxy.zap.extension.policyRuleImplementation;

import org.zaproxy.zap.extension.policyRuleImplementation.ruleImplementations.*;
import org.zaproxy.zap.extension.policyRuleVerifier.policies.AbstractPolicy;

public class PolicyImplementation extends AbstractPolicy {

    /**
     * Method that adds the rules in the policy to the rules list.
     */
    @Override
    public void setRules() {
        getRules().add(new HttpsTrafficRule());
        getRules().add(new CookieAttributesRule());
        getRules().add(new GenericEmailAddressRule());
        getRules().add(new ContainsKeyWordRule());
        getRules().add(new VisitsDomainRule());
        getRules().add(new CheckHTTPMethod());
        getRules().add(new CheckResponseCharacterNumber());
    }

    /**
     * Method that returns the name of the policy.
     */
    @Override
    public String getName() {
        return "Second Policy";
    }

}
