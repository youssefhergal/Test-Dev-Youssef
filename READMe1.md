# Test Dev Youssef

Ce projet est une application Java qui calcule la probabilité de succès d'un voyage interstellaire à l'aide de données provenant de deux fichiers JSON (`millennium-falcon.json` et `empire.json`). Il utilise également une base de données SQLite contenant des informations sur les routes disponibles dans l'univers.

---

## Fonctionnalités

- **Lecture de fichiers JSON** : Extraction des données de configuration pour le voyage interstellaire.
- **Interaction avec une base de données SQLite** : Chargement des routes et des connexions entre différents points.
- **Calcul de probabilité** : Utilisation d'algorithmes pour trouver les chemins les plus optimaux tout en prenant en compte les contraintes des chasseurs de primes et du carburant.
- **Messages de journalisation (Beta)** : Affichage des étapes et des résultats pour faciliter le suivi de l'exécution (En cours).

---

## Prérequis

### Outils nécessaires
- **Java Development Kit (JDK) 18 ou supérieur**
    - Vérifiez votre version installée avec :
      ```bash
      java -version
      ```
    - Assurez-vous que `JAVA_HOME` pointe vers le JDK installé.

- **Apache Maven**
    - Vérifiez l'installation avec :
      ```bash
      mvn -version
      ```

- **SQLite**
    - Si vous devez inspecter ou modifier la base de données, téléchargez un outil SQLite comme [DB Browser for SQLite](https://sqlitebrowser.org/).

---

## Installation et Configuration

1. **Clonez ce projet** :
   ```bash
   git clone https://github.com/youssefhergal/Test_Dev_Youssef.git
   cd Test_Dev_Youssef
   ```
2. **Compiler le programme** :
    ```bash
    mvn clean compile
    ```
3. **Exécution du Programme**:
    ```bash
    mvn exec:java -Dexec.mainClass="org.example.Main" -Dexec.args="Chemin_vers_json_file_falcon Chemin_vers_json_file_empire"
    ```
### ou via un fichier Jar exécutable
2. **Generer le jar Snapshot** :
    ```bash
    mvn clean package
    ```
3. **Exécution du Programme**:
    ```bash
    java -jar target/Test_Dev_Youssef-1.0-SNAPSHOT.jar chemin_vers-falcon.json chemin_vers_empire.json
    ```