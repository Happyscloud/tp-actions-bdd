# TP 3 - Réponses aux questions de réflexion

## 1. Quelle est la différence entre un job et une étape (step) ?
Un job est un ensemble d'étapes qui s'exécutent sur la même machine (le runner). Une étape (step) est une seule tâche dans un job, comme lancer une commande ou utiliser une action. Les étapes d'un job s'exécutent l'une après l'autre. Les jobs peuvent s'exécuter en parallèle.

## 2. Pourquoi les jobs python, node, java et php s'exécutent-ils en même temps ? Comment imposer un ordre ?
Ils s'exécutent en même temps parce que par défaut, GitHub Actions lance tous les jobs en parallèle pour gagner du temps. Pour imposer un ordre, on utilise le mot-clé `needs`. Par exemple, `build: needs: [python, node, java, php]` force le job build à attendre que les 4 autres soient terminés.

## 3. Pourquoi ne faut-il jamais écrire un mot de passe de production dans le fichier YAML ? Où faut-il le mettre ?
Parce que le fichier YAML est visible par tout le monde sur le dépôt (surtout s'il est public). N'importe qui pourrait voler le mot de passe. Il faut le mettre dans les "Secrets" de GitHub (Settings -> Secrets and variables -> Actions), et y accéder dans le workflow avec `${{ secrets.NOM_DU_SECRET }}`.

## 4. Pourquoi la base de données est-elle recréée à chaque exécution ? Quel est l'avantage pour les tests ?
La base est recréée à chaque exécution (avec `bash db/setup.sh`) pour être sûre de partir d'un état propre et connu. L'avantage, c'est que les tests sont isolés et reproductibles : on ne dépend pas de ce que quelqu'un a fait avant. Si un test ajoute un étudiant, il ne va pas fausser le test suivant.

## 5. Quelle est la différence entre un test unitaire et un test d'intégration ? De quel type sont les tests de ce TP ?
Un test unitaire teste une petite partie du code de façon isolée (sans base de données, sans réseau). Un test d'intégration teste plusieurs parties ensemble, comme le code et une vraie base de données. Les tests de ce TP sont des tests d'intégration, car ils utilisent la vraie base PostgreSQL.
