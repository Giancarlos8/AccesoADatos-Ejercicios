\# Práctica 9: Gestor de Productos CSV ampliado



Alumno: Giancarlos Vilchez  



1\. Descripción del Proyecto

Esta aplicación nos permite gestionar un catálogo de productos, guardar, leer, modificar, borrar información y también crear copias de seguridad.





2\. Estructura de Carpetas



GestorProductosRA1/

\-datos/          -> Carpeta con los ficheros (.csv y .tsv)

\-backups/        -> Carpeta para las copias de seguridad

\-src/            -> Código fuente en Java (GestorProductos, Producto, Exception)

\-README.md       -> Documentación del proyecto





3\. Cómo Ejecutar el Programa



Al arrancar, el programa creará automáticamente las carpetas `datos/` y `backups/` si no existen. y aparecerán varias opciones:



1\. Listar productos: Lee el fichero CSV y muestra por pantalla todos los productos registrados (ID, nombre y precio), también el de productos guardados.



2\. Añadir producto: Pide introducir el ID, un nombre y un precio. Verifica que el ID no esté repetido y que los datos sean correctos antes de guardarlo al final del fichero.



3\. Buscar producto por ID: Solicita un número de ID y busca en el fichero si existe, mostrando sus detalles en caso de encontrarlo.



4\. Modificar producto: Permite buscar un producto por su ID para actualizar su nombre o su precio, reescribiendo el fichero de forma segura.



5\. Eliminar producto: Pide el ID del producto que deseas borrar, lo busca y actualiza el fichero eliminando esa línea concreta.



6\. Convertir formatos (CSV / TSV): Permite transformar los datos al otro formato.



7\. Hacer copia de seguridad: Genera automáticamente un fichero de backup dentro de la carpeta backups/ añadiéndole la fecha y hora.



8\. Salir: Cierra la aplicación y finaliza la ejecución

