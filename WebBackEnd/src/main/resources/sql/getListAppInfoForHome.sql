SELECT *
FROM app_info a # 'o' from 'oldest version_code in group'
         LEFT JOIN app_info b # 'b' from 'bigger version_code'
                   ON a.package_name = b.package_name
                       AND a.app_type = b.app_type
                       AND a.app_name = b.app_name
                       AND IF(a.version_code < b.version_code, a.version_code < b.version_code, coalesce(a.update_date, a.create_date) < coalesce(b.update_date, b.create_date))
                       AND a.delete_flg = '0' AND b.delete_flg = '0'
WHERE
        a.delete_flg = '0'
  AND b.id is null # bigger version_code not found
order by coalesce(a.update_date, a.create_date) desc

# /*SELECT a.*
# FROM app_info a # 'o' from 'oldest version_code in group'
#          LEFT JOIN app_info b # 'b' from 'bigger version_code'
#                    ON a.package_name = b.package_name
#                        AND a.app_type = b.app_type
#                        AND a.app_name = b.app_name
#                        AND IF(a.version_code < b.version_code, a.version_code < b.version_code,
#                               coalesce(a.update_date, a.create_date) < coalesce(b.update_date, b.create_date))
#                        AND a.delete_flg = '0'
# WHERE b.version_code is null
#    or coalesce(b.update_date, b.create_date) is null # bigger version_code not found*/