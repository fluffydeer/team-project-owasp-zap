import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.reflections.Reflections;
import org.zaproxy.zap.extension.policyRuleImplementation.PolicyImplementation;
import org.zaproxy.zap.extension.policyRuleVerifier.policies.RuleInterface;

import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

public class TestPolicyImplementation {

    @InjectMocks
    private PolicyImplementation policyImplementation;

    @Before
    public void setup(){
        initMocks(this);
    }

    @Test
    public void getRules(){

        Reflections reflections = new Reflections
                ("org.zaproxy.zap.extension.policyRuleImplementation.ruleImplementations");

        Set<Class<? extends RuleInterface>> subTypes = reflections.getSubTypesOf(RuleInterface.class);

        policyImplementation.setRules();

        //add one because of the abstract rule class for the file checking.
        Assert.assertEquals(policyImplementation.getRules().size() + 1, subTypes.size());

    }

    @Test
    public void getName(){
        assertTrue(policyImplementation.getName() != null );
    }
}
