# Screen Time Widget

Widget Android minimaliste façon "Temps d'écran" iOS 26, style liquid glass, cohérent avec ton thème.

## Ce que ça fait
- Un widget à poser sur l'écran d'accueil qui affiche le temps d'écran total du jour ("2h 14min")
- Fond translucide type "verre" (liquid glass) avec léger reflet et bordure fine
- Un tap sur le widget ouvre l'appli pour rafraîchir ou gérer la permission

## Compiler l'APK sans ordinateur (via Termux + GitHub Actions)

Ce projet contient déjà `.github/workflows/build.yml` : GitHub compile l'APK gratuitement dans le cloud dès que le code est poussé sur un repo.

**1. Installe Termux** (depuis F-Droid, pas le Play Store — version Play Store obsolète) : https://f-droid.org/packages/com.termux/

**2. Dans Termux :**
```
pkg install git unzip -y
termux-setup-storage
cd storage/downloads
unzip ScreenTimeWidget.zip
cd ScreenTimeWidget
```

**3. Crée un dépôt vide sur GitHub** (github.com → New repository, ex. nom `screen-time-widget`, ne rien cocher)

**4. Pousse le code depuis Termux :**
```
git init
git add .
git commit -m "premier commit"
git branch -M main
git remote add origin https://github.com/TON_PSEUDO/screen-time-widget.git
git push -u origin main
```
GitHub demande une authentification : utilise un **Personal Access Token** (Settings → Developer settings → Personal access tokens → coche "repo") comme mot de passe.

**5. Récupère l'APK :**
- Repo GitHub → onglet **Actions**
- Le workflow "Build APK" tourne automatiquement, attends l'icône verte ✓
- Clique dessus → section "Artifacts" en bas → télécharge `app-debug`
- Décompresse, installe l'APK sur ton Motorola (autorise les sources inconnues si demandé)

## Après installation
1. Ouvre l'appli **Screen Time Widget**
2. Appuie sur "Activer l'accès aux stats d'usage" → active le toggle pour "Screen Time Widget" dans la liste Android
3. Reviens, appuie sur "Rafraîchir le widget"
4. Appui long sur l'écran d'accueil → Widgets → cherche "Screen Time Widget" → glisse-le où tu veux

## Notes
- Android impose un minimum de 30 min entre les rafraîchissements automatiques du widget (limite système, pas modifiable). Le bouton "Rafraîchir" dans l'appli force une mise à jour immédiate si besoin.
- Le style (couleurs, taille du texte, rayon des coins) se modifie facilement dans :
  - `app/src/main/res/drawable/widget_glass_background.xml` (le fond glass)
  - `app/src/main/res/layout/widget_screen_time.xml` (texte et disposition)
- Pas de connexion internet requise, tout tourne en local avec l'API Android `UsageStatsManager`.
