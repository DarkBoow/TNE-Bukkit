# The New Economy (TNE)

The New Economy est un plugin d\'économie modulaire pour serveurs Bukkit/Spigot. Il vise à fournir une solution complète et personnalisable grâce à un système de modules indépendants. La version fournie par le `plugin.yml` est la **0.1.1.9** et cible l\'API Bukkit **1.13**.

## Fonctionnement
- Système de modules activables ou désactivables simplement en déposant ou retirant les fichiers JAR.
- Gestion de comptes (joueurs et serveur), banques, coffres personnels, primes, plots, marché, intégration Discord, etc.
- Historique des transactions avec possibilité d\'annulation.
- Support des monnaies virtuelles ou basées sur des objets, multi-monde et multi-base de données (MySQL ou H2).
- API publique et compatibilité avec Vault et Reserve.

## Fichiers de configuration
Les configurations se trouvent dans `TNE/src/net/tnemc/resources/` ou dans chaque module.

### config.yml
- **Core** : options générales (UUID, multi-monde, monde par défaut, mode debug).
- **Server** : objet ouvrant le menu d\'action (`MenuMaterial`), consolidation automatique des items, gains d\'expérience, nom du serveur et compte serveur (`Server_Account`).
- **Commands** : déclencheurs des commandes et exclusions du classement `money top`.
- **Update** : vérification et notification des mises à jour.
- **Transactions** : format de date et fuseau horaire de l\'historique.
- **AutoSaver** : sauvegarde automatique (activation et intervalle).
- **Currency** : format des monnaies, symbole, type (virtuel, item, expérience), valeur initiale et limite maximale.
- **World** : coût éventuel lors des changements de monde.
- **Database** : type de base (MySQL ou H2), préfixe des tables, configuration MySQL.

### commands.yml
Décrit toutes les commandes et sous-commandes disponibles ainsi que leurs paramètres. Les commandes principales incluent `money`, `tne`, `currency`, `language`, `account`, `transaction`, `convert`...

### items.yml
Répertorie les items utilisables sur les panneaux de vente avec leurs noms, permissions d\'achat/vente et alias.

### messages.yml
Contient l\'ensemble des messages affichés par le plugin. Les codes couleurs au format `&` ou `<couleur>` sont pris en charge.

### players.yml
Permet de définir des règles spécifiques par joueur (par exemple le nombre maximum de panneaux autorisés).

### worlds.yml
Autorise la configuration de paramètres propres à chaque monde : désactivation de l\'économie, frais d\'accès, partage des soldes ou des paramètres...

### plugin.yml
Fichier de déclaration du plugin : nom, version, dépendances facultatives et surtout liste complète des permissions `tne.*` utilisées par les commandes.

### Autres fichiers
- `tne_tables.yml` : schéma des tables pour la base de données.
- `banks.yml` (module Banks) : multi-monde, liste noire de monnaies, braquages et banques régionales.
- `vaults.yml` (module Vaults) : coût et taille des coffres, packages optionnels.
- `bounty.yml` et `hunter.yml` (module Bounty) : paramètres des primes et niveaux de chasseur.
- `discord.yml` (module Discord) : identifiants de rôles et canal pour la journalisation des transactions.
- `convert.yml` (module Conversion) : informations nécessaires pour convertir une ancienne base de données.
- `plots.yml` (module Plots) : limites d\'achat de parcelles, coûts et revente.

## Fichiers de langue
Des fichiers de langue prêts à l\'emploi sont fournis dans `TNE/languages/` :
`Chinese.yml`, `Dutch.yml`, `French.yml`, `German.yml`, `Portuguese.yml`, `PT-BR.yml`, `Spanish.yml` et `Swedish.yml`. Chaque joueur peut choisir sa langue et l\'administrateur peut adapter les messages.

## Commandes
Les principales commandes sont déclarées dans `commands.yml` :
- `/money` : consulter son solde, payer un joueur, donner ou retirer de l\'argent (selon les permissions).
- `/tne` : commandes administrateur (gestion des comptes, sauvegarde, rechargement, modules...).
- `/currency` : liste et gestion des monnaies.
- `/account` : paramètres de compte (ex. PIN).
- `/language` : choix et rechargement des langues.
- `/transaction` : consulter l\'historique ou annuler une transaction.
- `/convert` : outils de conversion depuis une autre économie.

Chaque commande possède de nombreuses sous-commandes détaillées dans `commands.yml`. Les permissions associées se trouvent dans `plugin.yml`.

## Compilation / Installation
1. Cloner ce dépôt :
   ```bash
   git clone <repo>
   ```
2. Compiler chaque module souhaité avec Maven :
   ```bash
   mvn clean package
   ```
3. Les JAR générés se trouvent dans le dossier `target/` de chaque module (ex. `TNE/target/TNE-1.1.9.2.jar`).
4. Copier `TNE.jar` et les JAR des modules désirés dans le dossier `plugins/` de votre serveur Bukkit/Spigot puis redémarrer.

## Fichiers importants
- `net.tnemc.core.TNE` : classe principale chargée au démarrage du serveur.
- `commands/`, `listeners/`, `menu/` : contiennent la logique des commandes, des événements et des menus.
- Chaque module dispose de son propre dossier (`TNEMobs`, `TNEBanks`, `TNEVaults`, etc.) avec un fichier `module.tne` indiquant la classe à charger.
- Les configurations par défaut sont situées dans `TNE/src/net/tnemc/resources/`.

## Utilisation rapide
- Placez `TNE.jar` et les modules nécessaires dans `plugins/`.
- Démarrez le serveur puis ajustez `config.yml` et les autres fichiers selon vos besoins.
- Utilisez `/money` pour vérifier votre solde et `/tne` pour les commandes d\'administration.
- Le menu d\'action est accessible avec l\'objet défini par `MenuMaterial` (par défaut un lingot d\'or).

## Licence et contributions
Ce projet est distribué sous licence **Creative Commons Attribution-NonCommercial-NoDerivatives 4.0**. Consultez `License.txt` pour les détails. Toute contribution doit respecter le `CODE_OF_CONDUCT.md`. Les rapports de bugs et suggestions se font via les issues GitHub.
