using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using rpgengine.vue;
using rpgengine.moteur.services;
namespace rpgengine.moteur
{
    class JeuPartie : Moteur
    {
        public static Partie partie;

        public void AfficherMenuAccueil(List<Object> parameters) {
            vue.reset();

            MenuWidget menuAccueil = new MenuWidget(25, 8, 30, 4);
            menuAccueil.addOption("Nouvelle partie", NouvellePartie, null);
            menuAccueil.addOption("Charger une partie", ChargerPartie, null);
            menuAccueil.addOption("Retour au menu principal", play, null);
            vue.addWidget(menuAccueil);
            vue.setReady();
            vue.draw();
        }



        public void ChargerPartie(List<Object> parameters)
        {

            vue.reset();
            List<string> parties = ServicePartie.listerParties();
            if (parties.Count > 0)
            {
                MenuWidget listeParties= new MenuWidget(0, 8, 20, parties.Count + 1);
                foreach (string s in parties)
                {
                    List<Object> p = new List<Object>();
                    p.Add(s);
                    listeParties.addOption(s, ChoisirPartie, p);
                }
                vue.addWidget(listeParties);
            }
            vue.setReady();
            vue.draw();
        }



        public void ChoisirPartie(List<Object> parameters)
        {
            if (parameters != null && parameters.Count > 0)
            {
                string pname = parameters[0] as string;
                if (pname != null)
                {
                    Partie charge = ServicePartie.ChargerPartie(pname);
                    if (charge != null)
                    {
                        partie = charge;
                    }
                }
            }
            List<Object> p = new List<Object>();
            p.Add(partie);
            lancerPartie(p);
        }

        public void NouvellePartie(List<Object> parameters)
        {
            vue.reset();
            List<string> univers = ServiceUnivers.listerUnivers();
            if (univers.Count > 0)
            {
                MenuWidget listeUnivers = new MenuWidget(0, 8, 20, univers.Count + 1);
                foreach (string s in univers)
                {
                    List<Object> p = new List<Object>();
                    p.Add(s);
                    listeUnivers.addOption(s, ChoisirUnivers, p);
                }
                vue.addWidget(listeUnivers);
            }
            vue.setReady();
            vue.draw();
        }
        public void ChoisirUnivers(List<Object> parameters)
        {
            if (parameters != null && parameters.Count > 0)
            {
                string uname = parameters[0] as string;
                if (uname != null)
                {
                    Univers charge = ServiceUnivers.ChargerUnivers(uname);
                    if (charge != null)
                    {
                        univers = charge;
                        ServicesRPG.univers = univers;
                    }
                }
            }
            ChoixPersonnage(null);
        }
        public void ChoixPersonnage(List<Object> parameters)
        {
            vue.reset();
            LabelWidget titre = new LabelWidget(0, 0, 20, 3, "Catégorie de personnage");
            vue.addWidget(titre);

            List<Categorie> categories = univers.Categories;
            int y = 6;
            MenuWidget choixCategorie = new MenuWidget(0, y, 50, 1);
            
            foreach (Categorie q in categories)
            {
                List<Object> p = new List<Object>();
                p.Add(q);
                choixCategorie.addOption(q.NomCategorie, CreerPersonnage, p);
                choixCategorie.Height += 1;
                y++;
            }
            vue.addWidget(choixCategorie);
            vue.setReady();
            vue.draw();
        }

        public void CreerPersonnage(List<Object> parameters)
        {
            
            if (parameters != null && parameters.Count > 0)
            {
                Categorie c = parameters[0] as Categorie;
                if (c != null)
                {
                    vue.reset();

                    FormWidget formEtitionCategorie = new FormWidget(0, 5, "Nouvelle catégorie");
                    TextBoxWidget nomCategorie = new TextBoxWidget(50, "Nom", "categorie.nomCategorie");
                    nomCategorie.Value = c.NomCategorie;
                    TextareaWidget descriptionCategorie = new TextareaWidget(500, "Description", 50, 5, "categorie.description");
                    descriptionCategorie.Value = c.Description;
                    formEtitionCategorie.Datas.Add("categorie.id", c.Id.ToString());
                    nomCategorie.Focusable = false;
                    descriptionCategorie.Focusable = false;
                    formEtitionCategorie.addWidget(nomCategorie);
                    formEtitionCategorie.addWidget(descriptionCategorie);
                    
                    List<InstanceCaracteristique> instancesCaracteristiques = c.InstancesCaracteristiques;
                    int y = 10;
                    foreach (InstanceCaracteristique q in instancesCaracteristiques)
                    {
                        SliderWidget slide = new SliderWidget(0, 0, 50, "caracteristique." + q.Caracteristique.Id.ToString(), q.Caracteristique.Nom, 0, 10);
                        slide.Value = q.Niveau;
                        slide.Focusable = false;
                        formEtitionCategorie.addWidget(slide);

                        y += 2;
                    }

                    SelectListWidget selectCompetenceRequise = new SelectListWidget("Compétences", "categorie.competences");
                    int i = 0;
                    foreach (Competence comp in univers.Competences)
                    {
                        selectCompetenceRequise.addOption(comp.Nom, comp.Id);
                        if (c.aCompetence(comp))
                        {
                            selectCompetenceRequise.SelectedOptions[i] = true;
                            selectCompetenceRequise.buildValue();
                        }
                        i++;
                    }
                    selectCompetenceRequise.Focusable = false;
                    formEtitionCategorie.addWidget(selectCompetenceRequise);
                    formEtitionCategorie.FormUserMethod = CreerPartie;
                    formEtitionCategorie.ForwardAction = lancerPartie;

                    ButtonWidget validButton = new ButtonWidget(0, 0, 20, "Valider");
                    validButton.ActionHandler = formEtitionCategorie.endForm;

                    formEtitionCategorie.addWidget(validButton);
                    vue.addWidget(formEtitionCategorie);
                    vue.setReady();
                    vue.draw();


                }
            }
            
        }


        public void CreerPartie(Dictionary<string,string> datas, DelegateMethods.ActionForward forward)
        {
            Categorie c = ServiceCategorie.CreerCategorie(datas, univers);
            partie = new Partie();
            partie.PersoJoueur = c;
            partie.UniversJeu = univers;
            forward(null);
        }

        public void lancerPartie(List<object> parameters)
        {
            vue.reset();

           
            ButtonWidget retour = new ButtonWidget(0, 0, 30, "Retour menu principal");
            retour.ActionHandler = play;
            ButtonWidget save = new ButtonWidget(30, 0, 30, "Sauvegarder");
            save.ActionHandler = formulaireSauverPartie;
            PlateauWidget plateau = new PlateauWidget(0, 5, 25, 12);
            plateau.Focusable = true;
            plateau.loadSituations(partie.UniversJeu.Situations);
            mettreAJourSItuationsAccessibles(plateau);
            if (parameters != null && parameters.Count > 0)
            {
                Partie p = parameters[0] as Partie;
                if (p != null)
                {
                    plateau.DefineCurrentSituationByCoord(p.ColonneSituationCourrante, p.LigneSituationCourrante);
                    plateau.PlacePlayerInCurrentSituation(p.PositionDansSituation_x, p.PositionDansSituation_y);
                }
            }
            else
            {
                plateau.CurrentSituation = plateau.Situations[0];
                plateau.CurrentSituation.Focused = true;
            }
            plateau.OnPlayerMove = TraitementNouvellePosition;
            vue.addWidget(plateau);
            vue.addWidget(retour);
            vue.addWidget(save);
            vue.setReady();
            vue.draw();
        }


        public void SauverPartie(Dictionary<string,string> datas, DelegateMethods.ActionForward forward)
        {
            partie.NomPartie = datas["partie.nompartie"];
            ServicePartie.SauverPartie(partie);
            forward(null);
        }
        public void formulaireSauverPartie(List<Object> parameters)
        {
            vue.reset();
            FormWidget sauvegarde = new FormWidget(0, 0, "Sauvegarder");
            TextBoxWidget nomSauvegarde = new TextBoxWidget(20, "Nom de sauvegarde", "partie.nompartie");
            sauvegarde.FormUserMethod = SauverPartie;
            sauvegarde.ForwardAction = lancerPartie;

            sauvegarde.addWidget(nomSauvegarde);
            ButtonWidget validButton = new ButtonWidget(0, 0, 20, "Valider");
            validButton.ActionHandler = sauvegarde.endForm;

            sauvegarde.addWidget(validButton);

            ButtonWidget annuler = new ButtonWidget(0, 0, 20, "Annuler");
            annuler.ActionHandler = lancerPartie;

            vue.addWidget(sauvegarde);
            vue.addWidget(annuler);
            vue.setReady();
            vue.draw();

        }


        public void TraitementNouvellePosition(List<Object> parameters)
        {
            if (parameters != null && parameters.Count > 0)
            {
                SituationWidget s = parameters[0] as SituationWidget;
                if (s != null)
                {
                    partie.ColonneSituationCourrante = (s.X - s.Plateau.X) / s.Width;
                    partie.LigneSituationCourrante = (s.Y - s.Plateau.Y) / s.Height;
                    partie.PositionDansSituation_x = s.PlayerPosition.x;
                    partie.PositionDansSituation_y = s.PlayerPosition.y;

                    bool situationVide =  (s as VoidSituationWidget) != null;
                    if (s.IsPLayerCentered() && !situationVide)
                    {
                        Situation ss = ServiceSituation.findById(s.IdSituation, partie.UniversJeu);
                        if (!ss.Terminee && ServiceSituation.estAccessible(ss,partie.UniversJeu))
                        {
                            DeclancherSituation(ss);
                        }
                    }
                    
                }
            }
        }

        public void mettreAJourSItuationsAccessibles(PlateauWidget p)
        {
            foreach (SituationWidget s in p.Situations)
            {
                s.IsAccessible = ServiceSituation.estAccessible(ServiceSituation.findById(s.IdSituation, partie.UniversJeu),partie.UniversJeu);
            }
        }
        public void DeclancherSituationActionHandler(List<Object> parameters)
        {
            DeclancherSituation(parameters[0] as Situation);
        }


        public void DeclancherSituation(Situation situation)
        {
            Combat scombat = situation as Combat;
            Epreuve sepreuve = situation as Epreuve;
            Dialogue sdialogue = situation as Dialogue;

            if (sepreuve != null)
            {
                AfficherSituationEpreuve(sepreuve);
            }
            if (sdialogue != null)
            {
                AfficherSituationDialogue(sdialogue);
            }
            if (scombat != null)
            {
                AfficherDescriptionCombat(scombat);
            }

        }
        public void AfficherSituationEpreuve(Epreuve epreuve)
        {

            vue.reset();

            List<Choix> choix = epreuve.ChoixPossibles;

            LabelWidget titre = new LabelWidget(0, 0, epreuve.Nom.Length + 2, 2, epreuve.Nom);
            
            vue.addWidget(titre);

            TextWidget description = new TextWidget(0, 4, 100, 10, epreuve.Description);
            vue.addWidget(description);

            MenuWidget menuChoix = new MenuWidget(0, 15, 20, choix.Count+1);
            foreach (Choix c in choix)
            {
                List<Object> p = new List<Object>();
                p.Add(c);
                p.Add(epreuve);
                menuChoix.addOption(c.Nom, faireChoixEpreuve, p);
            }
            vue.addWidget(menuChoix);
            vue.setReady();
            vue.draw();
            
        }

        public void faireChoixEpreuve(List<Object> parameters)
        {
            Choix c = parameters[0] as Choix;
            Epreuve e = parameters[1] as Epreuve;
            int resultat = c.testerChoix(partie.PersoJoueur);
            AfficherResultatEpreuve(e, resultat);
        }
        public void AfficherResultatEpreuve(Epreuve e, int resultat)
        {

            vue.reset();

            if (resultat > 0)
            {
                e.Terminee = true;
                Popup popup = new Popup(20, 10, 50, 5, "Reussite", "Félicitation, vous avez réussi l'épreuve " + e.Nom + " !");
                ButtonWidget continuer = new ButtonWidget(0, 0, 0, "Continuer");
                continuer.ActionHandler = lancerPartie;
                List<Object> p = new List<Object>();
                p.Add(partie);
                continuer.ActionHandlerParams = p;
                popup.addButton(continuer);
                continuer.Focused = true;
                vue.addWidget(popup);
            }
            else
            {
                Popup popup = new Popup(20, 10, 50, 5, "Echec", "Dommage, vous n'avez pas réussi l'épreuve " + e.Nom + " ...");
                ButtonWidget continuer = new ButtonWidget(0, 0, 0, "Passer");
                continuer.ActionHandler = lancerPartie;
                List<Object> p = new List<Object>();
                p.Add(partie);
                continuer.ActionHandlerParams = p;
                popup.addButton(continuer);
                continuer.Focused = true;
                ButtonWidget reessai = new ButtonWidget(0, 0, 0, "Réessayer");
                reessai.ActionHandler = DeclancherSituationActionHandler;

                List<Object> p2 = new List<Object>();
                p2.Add(e);
                reessai.ActionHandlerParams = p2;
                popup.addButton(reessai);



                vue.addWidget(popup);
            }
            vue.setReady();
            vue.draw();
        }


        public void AfficherDescriptionCombat(Combat combat)
        {
            vue.reset();
            LabelWidget title = new LabelWidget(30, 5, combat.Nom.Length + 2, 2, combat.Nom);
            vue.addWidget(title);
            TextWidget description = new TextWidget(20, 10, 70, 10, combat.Description);
            vue.addWidget(description);
            ButtonWidget continuer = new ButtonWidget(30, 30, combat.Nom.Length + 2, "Continuer");


            continuer.ActionHandler = AfficherSituationCombat;
            List<Object> p = new List<Object>();
            p.Add(combat);
            continuer.ActionHandlerParams = p;
            vue.addWidget(continuer);
            vue.setReady();
            vue.draw();
        }

        public void AfficherSituationCombat(List<Object> parameters)
        {
            vue.reset();

            Combat combat = parameters[0] as Combat;

            SliderWidget vieJoueur = new SliderWidget(5, 5, 50, "", "", 0, 100);
            vieJoueur.Value = partie.PersoJoueur.PointsDeVie.ToString();
            vieJoueur.Focusable = false;

            SliderWidget viePnj = new SliderWidget(57, 5, 50, "", "", 0, 100);
            viePnj.Value = combat.Pnj.PointsDeVie.ToString();
            viePnj.Focusable = false;
            vue.addWidget(viePnj);
            vue.addWidget(vieJoueur);

            List<Choix> choixPossibles = ServiceSituation.ConstruireListeChoixPourCombat(combat,partie.PersoJoueur);
            MenuWidget menuChoix = new MenuWidget(20, 20, 30, choixPossibles.Count+1);
            foreach(Choix choix in choixPossibles){
                List<Object> p = new List<Object>();
                p.Add(choix);
                p.Add(combat);
                menuChoix.addOption(choix.Nom, faireTourCombat, p);
            }
            vue.addWidget(menuChoix);
            vue.setReady();
            vue.draw();
            
        }


        public void LancerActionCombat(Choix choix, Personnage attaquant, Personnage attaqued)
        {

            int forceDeFrappe = choix.testerChoix(attaquant);

            if (attaqued.PointsDeVie - forceDeFrappe < 0)
            {
                attaqued.PointsDeVie = 0;
            }
            else
            {
                attaqued.PointsDeVie -= forceDeFrappe;
            }

        }

        public void faireTourCombat(List<Object> parameters)
        {
            Choix choix = parameters[0] as Choix;
            Combat c = parameters[1] as Combat;

            LancerActionCombat(choix,partie.PersoJoueur, c.Pnj);
            if (c.Pnj.PointsDeVie > 0)
            {
                List<Choix> choixPnj=ServiceSituation.ConstruireListeChoixPourCombat(c,c.Pnj);
                int nbChoix = choixPnj.Count;
                if (nbChoix > 0)
                {
                    LancerActionCombat(choixPnj[((new Random()).Next(0, nbChoix - 1))], c.Pnj,partie.PersoJoueur);
                }   
            }


            if (partie.PersoJoueur.PointsDeVie == 0)
            {
                CombatPerdu(c);
            }
            else if (c.Pnj.PointsDeVie == 0)
            {
                CombatGagne(c);
            }
            else
            {
                List<Object> param = new List<Object>();
                param.Add(c);
                AfficherSituationCombat(param);
            }
            
        }


        public void CombatPerdu(Combat c)
        {
            vue.reset();
            Categorie cat = c.Pnj as Categorie;
            string nom = "";
            if(cat!=null){
                nom=cat.NomCategorie;
            }
            Popup p = new Popup(20, 10, 50, 6, "Combat perdu", "Tu a perdu ton combat contre " + nom + " ! honte à toi !");
            ButtonWidget button = new ButtonWidget(0, 0, 20, "Renaitre");
            List<Object> param = new List<Object>();
            param.Add(partie);
            button.ActionHandler = lancerPartie;
            button.ActionHandlerParams = param;
            p.addButton(button);
            vue.addWidget(p);
            vue.setReady();
            vue.draw();
        }

        public void CombatGagne(Combat c)
        {
            vue.reset();
            c.Terminee = true;
            Categorie cat = c.Pnj as Categorie;
            string nom = "";
            if (cat != null)
            {
                nom = cat.NomCategorie;
            }
            Popup p = new Popup(20, 10, 50, 6, "Combat gagné", "Félicitation, tu as mis la raclé à " + nom + " !");
            ButtonWidget button = new ButtonWidget(0, 0, 20, "Continuer");
            List<Object> param = new List<Object>();
            param.Add(partie);
            button.ActionHandler = lancerPartie;
            button.ActionHandlerParams = param;

            p.addButton(button);
            vue.addWidget(p);
            vue.setReady();
            vue.draw();
        }

        public void AfficherSituationDialogue(Dialogue dialogue)
        {

            vue.reset();

            List<Choix> choix = dialogue.ChoixPossibles;

            LabelWidget titre = new LabelWidget(0, 0, dialogue.Nom.Length + 2, 2, dialogue.Nom);

            vue.addWidget(titre);

            TextWidget description = new TextWidget(0, 4, 100, 10, dialogue.Message);
            vue.addWidget(description);

            MenuWidget menuChoix = new MenuWidget(0, 15, 20, choix.Count + 1);
            foreach (Choix c in choix)
            {

                List<Object> p = new List<Object>();
                p.Add(partie);
                menuChoix.addOption(c.Nom, lancerPartie, p);
            }
            vue.addWidget(menuChoix);
            vue.setReady();
            vue.draw();
            
        }


        

        
    }
}
