SELECT a.*
FROM app_info a
WHERE a.package_name = :packageName
  AND a.version_code = :versionCode
  AND a.delete_flg = :deleteFlg
ORDER BY COALESCE(a.update_date, a.create_date) desc