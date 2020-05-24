# pglp_5.1 JDBC (Java DataBase Connectivity)

![Test Image 4](https://www.javatpoint.com/images/jdbc.JPG)

Dans ce projet , nous utilisons l'API JDBC pour assurer la persistance. Pour cela, nous avons utiliser le SGBD derby pour la persistance des données.

## Description:

Dans cet exercie, pour chaque classe d'objet (Personnel, CompositePersonnels) j'ai crée sa classe dao (daoPersonnel, DaoCompositePersonnels) pour faire la liaison ente la couche métier et une couche gérant le stockage des données, logiquement nommée couche de données. 
Il s'agit là des opérations classiques de stockage : la création, la lecture, la modification et la suppression. Ces quatre tâches basiques sont souvent raccourcies à l'anglaise en CRUD. Pour cela j'ai utilisé la base de donneé Derby.