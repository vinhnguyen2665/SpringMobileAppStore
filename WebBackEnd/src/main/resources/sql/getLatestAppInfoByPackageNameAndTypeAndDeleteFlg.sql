SELECT a.*
FROM app_info a
WHERE a.package_name = :packageName
and a.delete_flg = :deleteFlg
and a.app_type = :type
ORDER BY COALESCE(a.update_date, a.create_date) desc