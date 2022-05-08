SELECT *
FROM app_info a
WHERE (:packageName is null OR a.package_name = :packageName)
  AND (:appType is null OR a.app_type = :appType)
  AND (:deleteFlg is null OR a.delete_flg = :deleteFlg)
ORDER BY id desc;