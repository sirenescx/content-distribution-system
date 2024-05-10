create
    or replace function update_timestamp_on_duplicate_guid()
    returns trigger as
$$
begin
    if
        exists(select 1
               from items
               where guid = new.guid)
    then
        update items
        set updated_at = (created_at)
        where guid = new.guid;
        return null;
    end if;
    return new;
end;
$$
    language plpgsql;


create trigger update_timestamp
    before insert or
        update of guid
    on items
    for each row
execute function update_timestamp_on_duplicate_guid();

