package dao;
import com.mongodb.BasicDBObject;
import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import org.apache.commons.lang.StringUtils;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.util.Map;
/**
 * Created by fabita on 3/30/2016.
 */
public class ElExpressionService {
    ExpressionFactory factory;
    SimpleContext context;

    public void initialize(Map<String,Object> values, Object object)
    {
        factory = new ExpressionFactoryImpl();
        context = new SimpleContext();
        context.setELResolver(new ThingResolver(object));
        if (values != null){
            for (Map.Entry<String, Object> entry : values.entrySet()) {
                context.setVariable(entry.getKey(), factory.createValueExpression(entry.getValue(), Object.class));
            }
        }
        try {
            context.setFunction("long", "toHexString", java.lang.Long.class.getMethod("toHexString", long.class));
            context.setFunction("String", "leftPad", StringUtils.class.getMethod("leftPad", String.class, int.class, String.class));
            context.setFunction("String", "upperCase", StringUtils.class.getMethod("upperCase", String.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public Object evaluate(String expression,boolean validateProperties)
    {
        Object result = "";
        try
        {
            ValueExpression e;
            e = factory.createValueExpression( context,expression,Object.class);
            result = e.getValue(context);
        }
        catch( de.odysseus.el.tree.TreeBuilderException el )
        {
            // when there is a parsing error
            el.printStackTrace();
        } catch (javax.el.PropertyNotFoundException er){
            // when UDFs used in formula don't exist in thingTypeFields, because they don't have values
            if (validateProperties) {
                throw er;
            } else {
                return null;
            }
        } catch (javax.el.ELException ex) {
            // when parameters for function do not match
            ex.printStackTrace();
        }
        return result;
    }

}
