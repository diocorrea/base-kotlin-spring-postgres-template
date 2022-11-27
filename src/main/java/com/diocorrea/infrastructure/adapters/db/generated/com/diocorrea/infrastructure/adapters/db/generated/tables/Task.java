/*
 * This file is generated by jOOQ.
 */
package com.diocorrea.infrastructure.adapters.db.generated.tables;


import com.diocorrea.infrastructure.adapters.db.generated.Keys;
import com.diocorrea.infrastructure.adapters.db.generated.Public;
import com.diocorrea.infrastructure.adapters.db.generated.tables.records.TaskRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Task extends TableImpl<TaskRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.task</code>
     */
    public static final Task TASK = new Task();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TaskRecord> getRecordType() {
        return TaskRecord.class;
    }

    /**
     * The column <code>public.task.id</code>.
     */
    public final TableField<TaskRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.task.name</code>.
     */
    public final TableField<TaskRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>public.task.created</code>.
     */
    public final TableField<TaskRecord, LocalDateTime> CREATED = createField(DSL.name("created"), SQLDataType.LOCALDATETIME(6), this, "");

    private Task(Name alias, Table<TaskRecord> aliased) {
        this(alias, aliased, null);
    }

    private Task(Name alias, Table<TaskRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.task</code> table reference
     */
    public Task(String alias) {
        this(DSL.name(alias), TASK);
    }

    /**
     * Create an aliased <code>public.task</code> table reference
     */
    public Task(Name alias) {
        this(alias, TASK);
    }

    /**
     * Create a <code>public.task</code> table reference
     */
    public Task() {
        this(DSL.name("task"), null);
    }

    public <O extends Record> Task(Table<O> child, ForeignKey<O, TaskRecord> key) {
        super(child, key, TASK);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<TaskRecord> getPrimaryKey() {
        return Keys.TASK_PKEY;
    }

    @Override
    public List<UniqueKey<TaskRecord>> getKeys() {
        return Arrays.<UniqueKey<TaskRecord>>asList(Keys.TASK_PKEY);
    }

    @Override
    public Task as(String alias) {
        return new Task(DSL.name(alias), this);
    }

    @Override
    public Task as(Name alias) {
        return new Task(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Task rename(String name) {
        return new Task(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Task rename(Name name) {
        return new Task(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<UUID, String, LocalDateTime> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
