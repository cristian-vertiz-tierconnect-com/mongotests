package dao;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

import javax.el.*;
import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by fabita on 3/30/2016.
 */
public class ThingResolver extends ELResolver {
    public Object object;
    static long count = 0;

    public ThingResolver(Object object) {
        this.object = object;
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property) {
//        System.out.println( "count: " + count + " base='" + base + "' property='" + property + "'" );
        Object value = null;

        if( base == null ) {
            if (object != null){
                if (object instanceof Map || object instanceof BasicDBObject){
                    value = new MapELResolver().getValue(context, object, property);
                } else {
                    value = new BeanELResolver().getValue(context, object, property);
                }
            } else {
                value = new MapELResolver().getValue(context, base, property);
            }
        } else {
            if (base instanceof Map || base instanceof BasicDBObject){
                value = new MapELResolver().getValue(context, base, property);
            } else if (base instanceof BasicDBList){
                if (property instanceof String){
                    for(int i = 0; i < ((BasicDBList)base).size(); i++){
                        value = getValue(context,((BasicDBList)base).get(i),property);
                    }
                } else {
                    value = new ListELResolver().getValue(context, base, property);
                }
            } else {
                value = new BeanELResolver().getValue(context, base, property);
            }
        }
        if (value == null){
            throw new PropertyNotFoundException("Cannot find property " + property);
        }
        context.setPropertyResolved(true );
        count++;

        return value;

    }

    @Override
    public Object invoke(ELContext context, Object base, Object method, Class<?>[] paramTypes, Object[] params) {
        return super.invoke(context, base, method, paramTypes, params);
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        return null;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object value) {

    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        return false;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        return null;
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        return null;
    }

    @Override
    public Object convertToType(ELContext context, Object obj, Class<?> targetType) {
        return super.convertToType(context, obj, targetType);
    }
}
