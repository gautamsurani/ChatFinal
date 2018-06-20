package io.realm;


import android.util.JsonReader;
import io.realm.RealmObjectSchema;
import io.realm.internal.ColumnInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@io.realm.annotations.RealmModule
class DefaultRealmModuleMediator extends RealmProxyMediator {

    private static final Set<Class<? extends RealmModel>> MODEL_CLASSES;
    static {
        Set<Class<? extends RealmModel>> modelClasses = new HashSet<Class<? extends RealmModel>>();
        modelClasses.add(com.nazpowerchat.models.User.class);
        modelClasses.add(com.nazpowerchat.models.Attachment.class);
        modelClasses.add(com.nazpowerchat.models.Message.class);
        modelClasses.add(com.nazpowerchat.models.Group.class);
        modelClasses.add(com.nazpowerchat.models.Chat.class);
        modelClasses.add(com.nazpowerchat.models.MyString.class);
        MODEL_CLASSES = Collections.unmodifiableSet(modelClasses);
    }

    @Override
    public RealmObjectSchema createRealmObjectSchema(Class<? extends RealmModel> clazz, RealmSchema realmSchema) {
        checkClass(clazz);

        if (clazz.equals(com.nazpowerchat.models.User.class)) {
            return io.realm.UserRealmProxy.createRealmObjectSchema(realmSchema);
        }
        if (clazz.equals(com.nazpowerchat.models.Attachment.class)) {
            return io.realm.AttachmentRealmProxy.createRealmObjectSchema(realmSchema);
        }
        if (clazz.equals(com.nazpowerchat.models.Message.class)) {
            return io.realm.MessageRealmProxy.createRealmObjectSchema(realmSchema);
        }
        if (clazz.equals(com.nazpowerchat.models.Group.class)) {
            return io.realm.GroupRealmProxy.createRealmObjectSchema(realmSchema);
        }
        if (clazz.equals(com.nazpowerchat.models.Chat.class)) {
            return io.realm.ChatRealmProxy.createRealmObjectSchema(realmSchema);
        }
        if (clazz.equals(com.nazpowerchat.models.MyString.class)) {
            return io.realm.MyStringRealmProxy.createRealmObjectSchema(realmSchema);
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public ColumnInfo validateTable(Class<? extends RealmModel> clazz, SharedRealm sharedRealm, boolean allowExtraColumns) {
        checkClass(clazz);

        if (clazz.equals(com.nazpowerchat.models.User.class)) {
            return io.realm.UserRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        }
        if (clazz.equals(com.nazpowerchat.models.Attachment.class)) {
            return io.realm.AttachmentRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        }
        if (clazz.equals(com.nazpowerchat.models.Message.class)) {
            return io.realm.MessageRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        }
        if (clazz.equals(com.nazpowerchat.models.Group.class)) {
            return io.realm.GroupRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        }
        if (clazz.equals(com.nazpowerchat.models.Chat.class)) {
            return io.realm.ChatRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        }
        if (clazz.equals(com.nazpowerchat.models.MyString.class)) {
            return io.realm.MyStringRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public List<String> getFieldNames(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(com.nazpowerchat.models.User.class)) {
            return io.realm.UserRealmProxy.getFieldNames();
        }
        if (clazz.equals(com.nazpowerchat.models.Attachment.class)) {
            return io.realm.AttachmentRealmProxy.getFieldNames();
        }
        if (clazz.equals(com.nazpowerchat.models.Message.class)) {
            return io.realm.MessageRealmProxy.getFieldNames();
        }
        if (clazz.equals(com.nazpowerchat.models.Group.class)) {
            return io.realm.GroupRealmProxy.getFieldNames();
        }
        if (clazz.equals(com.nazpowerchat.models.Chat.class)) {
            return io.realm.ChatRealmProxy.getFieldNames();
        }
        if (clazz.equals(com.nazpowerchat.models.MyString.class)) {
            return io.realm.MyStringRealmProxy.getFieldNames();
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public String getTableName(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(com.nazpowerchat.models.User.class)) {
            return io.realm.UserRealmProxy.getTableName();
        }
        if (clazz.equals(com.nazpowerchat.models.Attachment.class)) {
            return io.realm.AttachmentRealmProxy.getTableName();
        }
        if (clazz.equals(com.nazpowerchat.models.Message.class)) {
            return io.realm.MessageRealmProxy.getTableName();
        }
        if (clazz.equals(com.nazpowerchat.models.Group.class)) {
            return io.realm.GroupRealmProxy.getTableName();
        }
        if (clazz.equals(com.nazpowerchat.models.Chat.class)) {
            return io.realm.ChatRealmProxy.getTableName();
        }
        if (clazz.equals(com.nazpowerchat.models.MyString.class)) {
            return io.realm.MyStringRealmProxy.getTableName();
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E newInstance(Class<E> clazz, Object baseRealm, Row row, ColumnInfo columnInfo, boolean acceptDefaultValue, List<String> excludeFields) {
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        try {
            objectContext.set((BaseRealm) baseRealm, row, columnInfo, acceptDefaultValue, excludeFields);
            checkClass(clazz);

            if (clazz.equals(com.nazpowerchat.models.User.class)) {
                return clazz.cast(new io.realm.UserRealmProxy());
            }
            if (clazz.equals(com.nazpowerchat.models.Attachment.class)) {
                return clazz.cast(new io.realm.AttachmentRealmProxy());
            }
            if (clazz.equals(com.nazpowerchat.models.Message.class)) {
                return clazz.cast(new io.realm.MessageRealmProxy());
            }
            if (clazz.equals(com.nazpowerchat.models.Group.class)) {
                return clazz.cast(new io.realm.GroupRealmProxy());
            }
            if (clazz.equals(com.nazpowerchat.models.Chat.class)) {
                return clazz.cast(new io.realm.ChatRealmProxy());
            }
            if (clazz.equals(com.nazpowerchat.models.MyString.class)) {
                return clazz.cast(new io.realm.MyStringRealmProxy());
            }
            throw getMissingProxyClassException(clazz);
        } finally {
            objectContext.clear();
        }
    }

    @Override
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return MODEL_CLASSES;
    }

    @Override
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E obj, boolean update, Map<RealmModel, RealmObjectProxy> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(com.nazpowerchat.models.User.class)) {
            return clazz.cast(io.realm.UserRealmProxy.copyOrUpdate(realm, (com.nazpowerchat.models.User) obj, update, cache));
        }
        if (clazz.equals(com.nazpowerchat.models.Attachment.class)) {
            return clazz.cast(io.realm.AttachmentRealmProxy.copyOrUpdate(realm, (com.nazpowerchat.models.Attachment) obj, update, cache));
        }
        if (clazz.equals(com.nazpowerchat.models.Message.class)) {
            return clazz.cast(io.realm.MessageRealmProxy.copyOrUpdate(realm, (com.nazpowerchat.models.Message) obj, update, cache));
        }
        if (clazz.equals(com.nazpowerchat.models.Group.class)) {
            return clazz.cast(io.realm.GroupRealmProxy.copyOrUpdate(realm, (com.nazpowerchat.models.Group) obj, update, cache));
        }
        if (clazz.equals(com.nazpowerchat.models.Chat.class)) {
            return clazz.cast(io.realm.ChatRealmProxy.copyOrUpdate(realm, (com.nazpowerchat.models.Chat) obj, update, cache));
        }
        if (clazz.equals(com.nazpowerchat.models.MyString.class)) {
            return clazz.cast(io.realm.MyStringRealmProxy.copyOrUpdate(realm, (com.nazpowerchat.models.MyString) obj, update, cache));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public void insert(Realm realm, RealmModel object, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

        if (clazz.equals(com.nazpowerchat.models.User.class)) {
            io.realm.UserRealmProxy.insert(realm, (com.nazpowerchat.models.User) object, cache);
        } else if (clazz.equals(com.nazpowerchat.models.Attachment.class)) {
            io.realm.AttachmentRealmProxy.insert(realm, (com.nazpowerchat.models.Attachment) object, cache);
        } else if (clazz.equals(com.nazpowerchat.models.Message.class)) {
            io.realm.MessageRealmProxy.insert(realm, (com.nazpowerchat.models.Message) object, cache);
        } else if (clazz.equals(com.nazpowerchat.models.Group.class)) {
            io.realm.GroupRealmProxy.insert(realm, (com.nazpowerchat.models.Group) object, cache);
        } else if (clazz.equals(com.nazpowerchat.models.Chat.class)) {
            io.realm.ChatRealmProxy.insert(realm, (com.nazpowerchat.models.Chat) object, cache);
        } else if (clazz.equals(com.nazpowerchat.models.MyString.class)) {
            io.realm.MyStringRealmProxy.insert(realm, (com.nazpowerchat.models.MyString) object, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insert(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(com.nazpowerchat.models.User.class)) {
                io.realm.UserRealmProxy.insert(realm, (com.nazpowerchat.models.User) object, cache);
            } else if (clazz.equals(com.nazpowerchat.models.Attachment.class)) {
                io.realm.AttachmentRealmProxy.insert(realm, (com.nazpowerchat.models.Attachment) object, cache);
            } else if (clazz.equals(com.nazpowerchat.models.Message.class)) {
                io.realm.MessageRealmProxy.insert(realm, (com.nazpowerchat.models.Message) object, cache);
            } else if (clazz.equals(com.nazpowerchat.models.Group.class)) {
                io.realm.GroupRealmProxy.insert(realm, (com.nazpowerchat.models.Group) object, cache);
            } else if (clazz.equals(com.nazpowerchat.models.Chat.class)) {
                io.realm.ChatRealmProxy.insert(realm, (com.nazpowerchat.models.Chat) object, cache);
            } else if (clazz.equals(com.nazpowerchat.models.MyString.class)) {
                io.realm.MyStringRealmProxy.insert(realm, (com.nazpowerchat.models.MyString) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(com.nazpowerchat.models.User.class)) {
                    io.realm.UserRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.nazpowerchat.models.Attachment.class)) {
                    io.realm.AttachmentRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.nazpowerchat.models.Message.class)) {
                    io.realm.MessageRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.nazpowerchat.models.Group.class)) {
                    io.realm.GroupRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.nazpowerchat.models.Chat.class)) {
                    io.realm.ChatRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.nazpowerchat.models.MyString.class)) {
                    io.realm.MyStringRealmProxy.insert(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, RealmModel obj, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(com.nazpowerchat.models.User.class)) {
            io.realm.UserRealmProxy.insertOrUpdate(realm, (com.nazpowerchat.models.User) obj, cache);
        } else if (clazz.equals(com.nazpowerchat.models.Attachment.class)) {
            io.realm.AttachmentRealmProxy.insertOrUpdate(realm, (com.nazpowerchat.models.Attachment) obj, cache);
        } else if (clazz.equals(com.nazpowerchat.models.Message.class)) {
            io.realm.MessageRealmProxy.insertOrUpdate(realm, (com.nazpowerchat.models.Message) obj, cache);
        } else if (clazz.equals(com.nazpowerchat.models.Group.class)) {
            io.realm.GroupRealmProxy.insertOrUpdate(realm, (com.nazpowerchat.models.Group) obj, cache);
        } else if (clazz.equals(com.nazpowerchat.models.Chat.class)) {
            io.realm.ChatRealmProxy.insertOrUpdate(realm, (com.nazpowerchat.models.Chat) obj, cache);
        } else if (clazz.equals(com.nazpowerchat.models.MyString.class)) {
            io.realm.MyStringRealmProxy.insertOrUpdate(realm, (com.nazpowerchat.models.MyString) obj, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(com.nazpowerchat.models.User.class)) {
                io.realm.UserRealmProxy.insertOrUpdate(realm, (com.nazpowerchat.models.User) object, cache);
            } else if (clazz.equals(com.nazpowerchat.models.Attachment.class)) {
                io.realm.AttachmentRealmProxy.insertOrUpdate(realm, (com.nazpowerchat.models.Attachment) object, cache);
            } else if (clazz.equals(com.nazpowerchat.models.Message.class)) {
                io.realm.MessageRealmProxy.insertOrUpdate(realm, (com.nazpowerchat.models.Message) object, cache);
            } else if (clazz.equals(com.nazpowerchat.models.Group.class)) {
                io.realm.GroupRealmProxy.insertOrUpdate(realm, (com.nazpowerchat.models.Group) object, cache);
            } else if (clazz.equals(com.nazpowerchat.models.Chat.class)) {
                io.realm.ChatRealmProxy.insertOrUpdate(realm, (com.nazpowerchat.models.Chat) object, cache);
            } else if (clazz.equals(com.nazpowerchat.models.MyString.class)) {
                io.realm.MyStringRealmProxy.insertOrUpdate(realm, (com.nazpowerchat.models.MyString) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(com.nazpowerchat.models.User.class)) {
                    io.realm.UserRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.nazpowerchat.models.Attachment.class)) {
                    io.realm.AttachmentRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.nazpowerchat.models.Message.class)) {
                    io.realm.MessageRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.nazpowerchat.models.Group.class)) {
                    io.realm.GroupRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.nazpowerchat.models.Chat.class)) {
                    io.realm.ChatRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.nazpowerchat.models.MyString.class)) {
                    io.realm.MyStringRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> clazz, Realm realm, JSONObject json, boolean update)
        throws JSONException {
        checkClass(clazz);

        if (clazz.equals(com.nazpowerchat.models.User.class)) {
            return clazz.cast(io.realm.UserRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.nazpowerchat.models.Attachment.class)) {
            return clazz.cast(io.realm.AttachmentRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.nazpowerchat.models.Message.class)) {
            return clazz.cast(io.realm.MessageRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.nazpowerchat.models.Group.class)) {
            return clazz.cast(io.realm.GroupRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.nazpowerchat.models.Chat.class)) {
            return clazz.cast(io.realm.ChatRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.nazpowerchat.models.MyString.class)) {
            return clazz.cast(io.realm.MyStringRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E createUsingJsonStream(Class<E> clazz, Realm realm, JsonReader reader)
        throws IOException {
        checkClass(clazz);

        if (clazz.equals(com.nazpowerchat.models.User.class)) {
            return clazz.cast(io.realm.UserRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.nazpowerchat.models.Attachment.class)) {
            return clazz.cast(io.realm.AttachmentRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.nazpowerchat.models.Message.class)) {
            return clazz.cast(io.realm.MessageRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.nazpowerchat.models.Group.class)) {
            return clazz.cast(io.realm.GroupRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.nazpowerchat.models.Chat.class)) {
            return clazz.cast(io.realm.ChatRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.nazpowerchat.models.MyString.class)) {
            return clazz.cast(io.realm.MyStringRealmProxy.createUsingJsonStream(realm, reader));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E createDetachedCopy(E realmObject, int maxDepth, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) realmObject.getClass().getSuperclass();

        if (clazz.equals(com.nazpowerchat.models.User.class)) {
            return clazz.cast(io.realm.UserRealmProxy.createDetachedCopy((com.nazpowerchat.models.User) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.nazpowerchat.models.Attachment.class)) {
            return clazz.cast(io.realm.AttachmentRealmProxy.createDetachedCopy((com.nazpowerchat.models.Attachment) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.nazpowerchat.models.Message.class)) {
            return clazz.cast(io.realm.MessageRealmProxy.createDetachedCopy((com.nazpowerchat.models.Message) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.nazpowerchat.models.Group.class)) {
            return clazz.cast(io.realm.GroupRealmProxy.createDetachedCopy((com.nazpowerchat.models.Group) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.nazpowerchat.models.Chat.class)) {
            return clazz.cast(io.realm.ChatRealmProxy.createDetachedCopy((com.nazpowerchat.models.Chat) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.nazpowerchat.models.MyString.class)) {
            return clazz.cast(io.realm.MyStringRealmProxy.createDetachedCopy((com.nazpowerchat.models.MyString) realmObject, 0, maxDepth, cache));
        }
        throw getMissingProxyClassException(clazz);
    }

}
