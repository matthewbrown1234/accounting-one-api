create view account_entry_view as
    select ae.*, a.account_name
    from account_entry ae
    join account a on ae.account_id = a.id
