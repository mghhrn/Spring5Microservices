package com.order.dao;

import com.order.model.IModel;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.jooq.UpdatableRecord;
import org.jooq.impl.DAOImpl;
import org.simpleflatmapper.jdbc.JdbcMapper;
import org.simpleflatmapper.jdbc.JdbcMapperFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.partitioningBy;

/**
 * Class used to share properties and methods among all existing Daos
 */
public abstract class ParentDao<R extends UpdatableRecord<R>, P extends IModel, T> extends DAOImpl<R, P, T> {

    // Used to execute SQL queries in database
    protected DSLContext dsl;


    protected ParentDao(Table<R> table, Class<P> type, DSLContext dslContext) {
        super(table, type, dslContext.configuration());
        this.dsl = dslContext;
    }

    /**
     * {@inheritDoc}
     */
    protected abstract T getId(P var1);


    /**
     *    Return a {@link JdbcMapper} used to transform raw information given by a database query into a
     * desired Java object.
     *
     * @param classT
     *    {@link Class} of the returned results.
     * @param columns
     *    {@link String}s used to match information of the query into returned object
     * @param <T>
     *    Type of the returned results
     *
     * @return a Java object of type T
     */
    protected <T> JdbcMapper<T> getJdbcMapper(Class<T> classT, String... columns) {
        return JdbcMapperFactory.newInstance().addKeys(columns).newMapper(classT);
    }


    /**
     *    Store in database the given object, choosing between {@code insert} or {@code update} if
     * this one is a new instance or not.
     *
     * @param object
     *    Information to save in database
     */
    public void save(P object) {
        Optional.ofNullable(object)
                .ifPresent(o -> {
                    if (o.isNew()) {
                        insert(o);
                    } else {
                        update(o);
                    }
                });
    }


    /**
     * Store in database the given {@link Collection}.
     *
     * @param objects
     *    {@link Collection} of objects to store in database
     */
    public void saveAll(Collection<P> objects) {
        Optional.ofNullable(objects)
                .ifPresent(o -> {
                    Map<Boolean, List<P>> insertAndUpdate =
                            objects.stream().collect(partitioningBy(IModel::isNew));

                    List<P> toInsert = insertAndUpdate.get(true);
                    if (null != toInsert)
                        insert(toInsert);

                    List<P> toUpdate = insertAndUpdate.get(false);
                    if (null != toUpdate)
                       update(toUpdate);
                });
    }

}