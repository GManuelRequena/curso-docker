#Execute this Script in order to create MSSQL container and then the database for msvc-usuarios
docker run -d -p 1434:1433 --name mssql --network spring -e ACCEPT_EULA=Y -e MSSQL_SA_PASSWORD="Sasa2032" -e MSSQL_TCP_PORT=1433 -v data-mssql:/var/lib/mssql --restart=always mcr.microsoft.com/mssql/server
Start-Sleep 20
docker exec -it mssql /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P 'Sasa2032' -Q 'create database msvc_usuarios'