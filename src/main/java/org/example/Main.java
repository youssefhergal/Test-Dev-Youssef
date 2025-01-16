package org.example;

import java.nio.file.*;
import java.util.*;

import static org.example.Utils.readJson;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Démarrage du programme...");

            if (args.length < 2) {
                System.out.println("Usage: java -jar <jar-file> <arg1> <arg2>");
                return;
            }
            System.out.println("Argument 1: " + args[0]);
            System.out.println("Argument 2: " + args[1]);

            // Définir le chemin des fichiers JSON
            String falconJsonPath = args[0];
            String empireJsonPath = args[1];
            String dbPath = "src/main/examples/example2/universe.db";

            // Lire les fichiers JSON
            Map<String, Object> falconData = readJson(falconJsonPath);
            Map<String, Object> empireData = readJson(empireJsonPath);
            System.out.println("Fichiers JSON chargés avec succès");

            // Extraction des données de falconData
            // Gestion du type pour "autonomy"
            Object autonomyObj = falconData.get("autonomy");
            int autonomy = (autonomyObj instanceof Double) ? ((Double) autonomyObj).intValue() : (int) autonomyObj;

            String departure = (String) falconData.get("departure");
            String arrival = (String) falconData.get("arrival");


            // Extraction des données de empireData
            // Gestion du type pour "countdown"
            Object countdownObj = empireData.get("countdown");
            int countdown = (countdownObj instanceof Double) ? ((Double) countdownObj).intValue() : (int) countdownObj;

            List<Map<String, Object>> bountyHunters = (List<Map<String, Object>>) empireData.get("bounty_hunters");

            System.out.println("Données extraites avec succès");

            // Charger la base de données SQLite avec Graph
            Graph graph = new Graph(dbPath);

            // Calculer la probabilité
            double probability = Utils.findRoutes(graph, departure, arrival, autonomy, countdown, bountyHunters);

            // Afficher le résultat
            System.out.println("La probabilité est de : " + (int) (probability * 100) + "%");

        } catch (Throwable e) {
            // Afficher les détails de l'exception pour faciliter le débogage
            System.err.println("Erreur pendant l'exécution :");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
