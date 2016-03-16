using rpgengine.moteur.services;
using rpgengine.univers;
using rpgengine.vue;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.moteur
{
    class GestionUnivers : Moteur
    {
        public void SauverUnivers(List<Object> parameters)
        {
            ServiceUnivers.SauverUnivers(univers);
        }

        public void CreerUnivers(Dictionary<string, string> datas, DelegateMethods.ActionForward forward)
        {
            univers = ServiceUnivers.CreerUnivers(datas);
            forward(null);
        }
        public void CreerCaracteristique(Dictionary<string, string> datas, DelegateMethods.ActionForward forward)
        {
            univers.ajouterCararacteristique(ServiceCaracteristique.CreerCaracteristique(datas));
            forward(null);
        }
        public void CreerCompetence(Dictionary<string, string> datas, DelegateMethods.ActionForward forward)
        {
            univers.ajouterCompetence(ServiceCompetence.CreerCompetence(datas));
            forward(null);
        }
        public void CreerCategorie(Dictionary<string, string> datas, DelegateMethods.ActionForward forward)
        {
            univers.ajouterCategorie(ServiceCategorie.CreerCategorie(datas, univers));
            forward(null);
        }
        public void ModifierCaracteristique(Dictionary<string, string> datas, DelegateMethods.ActionForward forward)
        {
            if (datas["caracteristique.id"] != null)
            {
                ServiceCaracteristique.ModifierCaracteristique(datas, ServiceCaracteristique.findById(univers, int.Parse(datas["caracteristique.id"])));
            }
            forward(null);
        }
        public void ModifierCompetence(Dictionary<string, string> datas, DelegateMethods.ActionForward forward)
        {
            if (datas["competence.id"] != null)
            {
                ServiceCompetence.ModifierCompetence(datas, ServiceCompetence.findById(univers, int.Parse(datas["competence.id"])));
            }
            forward(null);
        }
        public void ModifierCategorie(Dictionary<string, string> datas, DelegateMethods.ActionForward forward)
        {
            if (datas["categorie.id"] != null)
            {
                ServiceCategorie.ModifierCategorie(datas, univers, ServiceCategorie.findById(univers, long.Parse(datas["categorie.id"])));
            }
            forward(null);
        }

        public void AfficherEditionUnivers(List<Object> parameters)
        {
            vue.reset();
            
            if (parameters != null && parameters.Count > 0)
            {
                string uname = parameters[0] as string;
                if (uname != null)
                {                    
                    Univers charge = ServiceUnivers.ChargerUnivers(uname);
                    if (charge != null)
                    {
                        univers = charge;
                    }
                    else
                    {
                        univers = new Univers();   
                    }
                }
                ServicesRPG.univers = univers;
            }
            LabelWidget TitrePage = new LabelWidget(35, 5, 50, 2, "EDITION DE L'UNIVERS " + univers.Nom);
            TitrePage.HasBorder = false;
            vue.addWidget(TitrePage);
            MenuWidget menuEdition = new MenuWidget(35, 10, 50, 5);
            menuEdition.addOption("Caractéristiques des personnages", AfficherEditionCaracteristiques, null);
            menuEdition.addOption("Compétences des personnages", AfficherEditionCompetences, null);
            menuEdition.addOption("Personnages", AfficherEditionCategoriesPersonnages, null);
            menuEdition.addOption("Ecrire l'histoire", AfficherEditionSituations, null);
            menuEdition.IsCenteredText = false;

            
            vue.addWidget(menuEdition);
            MenuWidget m = new MenuWidget(35, 17, 50, 3);
            
            m.addOption("Sauvegarder", SauverUnivers, null);
            m.addOption("Retour", AfficherGestionUnivers, null);
            vue.addWidget(m);
            vue.setReady();
            vue.draw();
        }

        public void AfficherEditionCategoriesPersonnages(List<Object> parameters)
        {

            vue.reset();

            LabelWidget TitrePage = new LabelWidget(35, 5, 50, 2, "Personnages");
            vue.addWidget(TitrePage);

            List<Categorie> categories = univers.Categories;
            int y = 10;
            if (categories.Count > 0)
            {
                MenuWidget choixCategorie = new MenuWidget(35, y, 50, categories.Count+1);
                foreach (Categorie q in categories)
                {
                    List<Object> p = new List<Object>();
                    p.Add(q);
                    choixCategorie.addOption(q.NomCategorie, EditerCategorie, p);
                    y++;
                }
                vue.addWidget(choixCategorie);
            }
            MenuWidget m = new MenuWidget(35, y + 3, 50, 3);
            m.addOption("Ajouter un personnage", AjouterCategorie, null);
            m.addOption("Retour", AfficherEditionUnivers, null);
            vue.addWidget(m);
            vue.setReady();
            vue.draw();
        }

        public void EditerCategorie(List<Object> parameters)
        {

            vue.reset();

            if (parameters != null && parameters.Count > 0)
            {
                Categorie aEditer = parameters[0] as Categorie;
                if (aEditer != null)
                {
                    LabelWidget TitrePage = new LabelWidget(35, 2, 50, 2, "Editer un personnage ");
                    TitrePage.HasBorder = false;
                    FormWidget formEtitionCategorie = new FormWidget(35, 5, "Personnage à éditer");
                    TextBoxWidget nomCategorie = new TextBoxWidget(50, "Nom", "categorie.nomCategorie");
                    nomCategorie.Value = aEditer.NomCategorie;
                    TextareaWidget descriptionCategorie = new TextareaWidget(500, "Description", 50, 5, "categorie.description");
                    descriptionCategorie.Value = aEditer.Description;
                    formEtitionCategorie.Datas.Add("categorie.id", aEditer.Id.ToString());
                    formEtitionCategorie.addWidget(nomCategorie);
                    formEtitionCategorie.addWidget(descriptionCategorie);
                    List<InstanceCaracteristique> instancesCaracteristiques = aEditer.InstancesCaracteristiques;
                    int y = 10;
                    foreach (InstanceCaracteristique q in instancesCaracteristiques)
                    {
                        SliderWidget slide = new SliderWidget(0, 0, 50, "caracteristique." + q.Caracteristique.Id.ToString(), q.Caracteristique.Nom, 0, 10);
                        slide.Value = q.Niveau;
                        formEtitionCategorie.addWidget(slide);
                        y += 2;
                    }
                    SelectListWidget selectCompetenceRequise = new SelectListWidget("Compétences", "categorie.competences");
                    int i = 0 ;
                    foreach (Competence c in univers.Competences)
                    {
                        selectCompetenceRequise.addOption(c.Nom, c.Id);
                        if (aEditer.aCompetence(c))
                        {
                            selectCompetenceRequise.SelectedOptions[i] = true;
                        }
                        i++;
                    }

                    formEtitionCategorie.addWidget(selectCompetenceRequise);
                    formEtitionCategorie.FormUserMethod = ModifierCategorie;
                    formEtitionCategorie.ForwardAction = AfficherEditionCategoriesPersonnages;
                    ButtonWidget validButton = new ButtonWidget(0, 0, 20, "Valider");


                    validButton.ActionHandler = formEtitionCategorie.endForm;

                    formEtitionCategorie.addWidget(validButton);
                    vue.addWidget(TitrePage);
                    vue.addWidget(formEtitionCategorie);
                    ButtonWidget retour = new ButtonWidget(34, formEtitionCategorie.Y + formEtitionCategorie.Height+1,8, "Retour");
                    retour.ActionHandler = AfficherEditionCategoriesPersonnages;
                    vue.addWidget(retour);
                    vue.setReady();
                    vue.draw();
                }
            }
        }

        public void AjouterCategorie(List<Object> parameters)
        {
            vue.reset();

            LabelWidget TitrePage = new LabelWidget(35, 2, 50, 2, "Ajouter une categorie ");
            TitrePage.HasBorder = false;
            FormWidget formAjoutCategorie = new FormWidget(35, 5, "Nouvelle catégorie");
            TextBoxWidget nomCategorie = new TextBoxWidget(50, "Nom", "categorie.nomCategorie");
            TextareaWidget descriptionCategorie = new TextareaWidget(500, "Description", 50, 10, "categorie.description");
            formAjoutCategorie.addWidget(nomCategorie);
            formAjoutCategorie.addWidget(descriptionCategorie);
            List<Caracteristique> caracteristiques = univers.Caracteristiques;
            int y = 15;
            foreach (Caracteristique q in caracteristiques)
            {
                SliderWidget slide = new SliderWidget(0, 0, 50, "caracteristique." + q.Id.ToString(), q.Nom, 0, 10);
                formAjoutCategorie.addWidget(slide);
                y += 2;
            }
            formAjoutCategorie.FormUserMethod = CreerCategorie;
            formAjoutCategorie.ForwardAction = AfficherEditionCategoriesPersonnages;

            SelectListWidget selectCompetenceRequise = new SelectListWidget("Compétences", "categorie.competences");
            foreach (Competence c in univers.Competences)
            {
                selectCompetenceRequise.addOption(c.Nom, c.Id);
            }
            formAjoutCategorie.addWidget(selectCompetenceRequise);
            ButtonWidget validButton = new ButtonWidget(0, 0, 20, "Valider");
            validButton.ActionHandler = formAjoutCategorie.endForm;
            formAjoutCategorie.addWidget(validButton);
            vue.addWidget(TitrePage);
            vue.addWidget(formAjoutCategorie);
            ButtonWidget retour = new ButtonWidget(34, formAjoutCategorie.Y + formAjoutCategorie.Height+1, 8, "Retour");
            retour.ActionHandler = AfficherEditionCategoriesPersonnages;
            vue.addWidget(retour);
            vue.setReady();
            vue.draw();
        }
            
        public void AfficherEditionCaracteristiques(List<Object> parameters)
        {

            vue.reset();
            LabelWidget TitrePage = new LabelWidget(35, 5, 50, 2, "CARACTERISTIQUES " + univers.Nom);
            vue.addWidget(TitrePage);

            List<Caracteristique> caracteristiques = univers.Caracteristiques;
            int y = 10;
            if (caracteristiques.Count > 0)
            {
                LabelWidget lab = new LabelWidget(35, 8, 10, 2, "Caractéristiques :");
                lab.HasBorder = false;
                vue.addWidget(lab);
                MenuWidget choixCaracteristiques = new MenuWidget(35, y, 50, caracteristiques.Count + 1);
                foreach (Caracteristique q in caracteristiques)
                {
                    List<Object> p = new List<Object>();
                    p.Add(q);
                    choixCaracteristiques.addOption(q.Nom, this.EtiderCaracteristique, p);
                    y++;
                }
                choixCaracteristiques.IsCenteredText = false;
                vue.addWidget(choixCaracteristiques);
            }
            MenuWidget menu = new MenuWidget(35, y + 3, 50, 3);

            menu.addOption("Ajouter une caractéristique", AjouterCaracteristique, null);
            menu.addOption("Retour", AfficherEditionUnivers, null);
            
            vue.addWidget(menu);
            vue.setReady();
            vue.draw();
        }

        public void EtiderCaracteristique(List<Object> parameters)
        {
            vue.reset();
            
            if (parameters != null && parameters.Count > 0)
            {
                Caracteristique aEditer = parameters[0] as Caracteristique;
                if (aEditer != null)
                {
                    LabelWidget TitrePage = new LabelWidget(35, 5, 50, 2, "Modifier une caractéristique ");
                    TitrePage.HasBorder = false;
                    FormWidget formAjoutCaracteristique = new FormWidget(35, 10, "Caractéristique");
                    TextBoxWidget nomCaracteristique = new TextBoxWidget(50, "Nom", "caracteristique.nom");
                    formAjoutCaracteristique.Datas.Add("caracteristique.id", aEditer.Id.ToString());
                    nomCaracteristique.Value = aEditer.Nom;
                    TextareaWidget descriptionCaracteristique = new TextareaWidget(500, "Description", 50, 10, "caracteristique.description");
                    descriptionCaracteristique.Value = aEditer.Description;
                    formAjoutCaracteristique.addWidget(nomCaracteristique);
                    formAjoutCaracteristique.addWidget(descriptionCaracteristique);
                    formAjoutCaracteristique.FormUserMethod = ModifierCaracteristique;
                    formAjoutCaracteristique.ForwardAction = AfficherEditionCaracteristiques;


                    ButtonWidget validButton = new ButtonWidget(0, 0, 20, "Valider");
                    validButton.ActionHandler = formAjoutCaracteristique.endForm;

                    formAjoutCaracteristique.addWidget(validButton);
                    vue.addWidget(TitrePage);
                    vue.addWidget(formAjoutCaracteristique);
                    ButtonWidget retour = new ButtonWidget(34, formAjoutCaracteristique.Y + formAjoutCaracteristique.Height+1, 8, "Retour");
                    retour.ActionHandler = AfficherEditionCaracteristiques;
                    vue.addWidget(retour);
                    vue.setReady();
                    vue.draw();
                }
            }
        }

        public void AjouterCaracteristique(List<Object> parameters)
        {

            vue.reset();
            
            LabelWidget TitrePage = new LabelWidget(35, 5, 50, 2, "Ajouter une caractéristique ");
            TitrePage.HasBorder = false;
            FormWidget formAjoutCaracteristique = new FormWidget(35, 10, "Nouvelle caractéristique");
            TextBoxWidget nomCaracteristique = new TextBoxWidget(50, "Nom", "caracteristique.nom");
            TextareaWidget descriptionCaracteristique = new TextareaWidget(500, "Description", 50, 10, "caracteristique.description");
            formAjoutCaracteristique.addWidget(nomCaracteristique);
            formAjoutCaracteristique.addWidget(descriptionCaracteristique);
            formAjoutCaracteristique.FormUserMethod = CreerCaracteristique;
            formAjoutCaracteristique.ForwardAction = AfficherEditionCaracteristiques;

            ButtonWidget validButton = new ButtonWidget(0, 0, 20, "Valider");
            validButton.ActionHandler = formAjoutCaracteristique.endForm;
            formAjoutCaracteristique.addWidget(validButton);

            vue.addWidget(TitrePage);
            vue.addWidget(formAjoutCaracteristique);
            ButtonWidget retour = new ButtonWidget(34, 1+formAjoutCaracteristique.Y + formAjoutCaracteristique.Height, 8, "Retour");
            retour.ActionHandler = AfficherEditionCaracteristiques;
            vue.addWidget(retour);
            vue.setReady();
            vue.draw();
        }

        public void EtiderCompetence(List<Object> parameters)
        {

            vue.reset();
            
            if (parameters != null && parameters.Count > 0)
            {
                Competence aEditer = parameters[0] as Competence;
                if (aEditer != null)
                {

                    LabelWidget TitrePage = new LabelWidget(35, 5, 50, 2, "Modifier une compétence ");
                    TitrePage.HasBorder = false;
                    FormWidget formModifCompetence = new FormWidget(35, 10, "Compétence");
                    TextBoxWidget nomCompetence = new TextBoxWidget(50, "Nom", "competence.nom");
                    formModifCompetence.Datas.Add("competence.id", aEditer.Id.ToString());
                    nomCompetence.Value = aEditer.Nom;
                    TextareaWidget descriptionCompetence = new TextareaWidget(500, "Description", 50, 10, "competence.description");
                    descriptionCompetence.Value = aEditer.Description;

                    SliderWidget points = new SliderWidget(0, 0, 50, "competences.points", "Efficacite", 0, 10);
                    points.Value = aEditer.InUsePoints.ToString();
                    formModifCompetence.addWidget(nomCompetence);
                    formModifCompetence.addWidget(descriptionCompetence);
                    formModifCompetence.addWidget(points);
                    formModifCompetence.FormUserMethod = ModifierCompetence;
                    formModifCompetence.ForwardAction = AfficherEditionCompetences;

                    ButtonWidget validButton = new ButtonWidget(0, 0, 20, "Valider");
                    validButton.ActionHandler = formModifCompetence.endForm;
                    formModifCompetence.addWidget(validButton);
                    vue.addWidget(TitrePage);
                    vue.addWidget(formModifCompetence);
                    ButtonWidget retour = new ButtonWidget(34, formModifCompetence.Y + formModifCompetence.Height+1, 8, "Retour");
                    retour.ActionHandler = AfficherEditionCompetences;
                    vue.addWidget(retour);
                    vue.setReady();
                    vue.draw();
                }
            }
        }

        public void AjouterCompetence(List<Object> parameters)
        {

            vue.reset();

            LabelWidget TitrePage = new LabelWidget(35, 5, 50, 2, "Ajouter une compétence ");
            TitrePage.HasBorder = false;
            FormWidget formAjoutCompetence = new FormWidget(35, 10, "Nouvelle compétence");
            TextBoxWidget nomCompetence = new TextBoxWidget(50, "Nom", "competence.nom");
            TextareaWidget descriptionCompetence = new TextareaWidget(500, "Description", 50, 10, "competence.description");

            SliderWidget points = new SliderWidget(0, 0, 50, "competences.points", "Efficacite", 0, 10);
            
            formAjoutCompetence.addWidget(nomCompetence);
            formAjoutCompetence.addWidget(descriptionCompetence);
            formAjoutCompetence.addWidget(points);
            formAjoutCompetence.FormUserMethod = CreerCompetence;
            formAjoutCompetence.ForwardAction = AfficherEditionCompetences;

            ButtonWidget validButton = new ButtonWidget(0, 0, 20, "Valider");
            validButton.ActionHandler = formAjoutCompetence.endForm;
            formAjoutCompetence.addWidget(validButton);
            vue.addWidget(TitrePage);
            vue.addWidget(formAjoutCompetence);
            ButtonWidget retour = new ButtonWidget(34, formAjoutCompetence.Y + formAjoutCompetence.Height+1, 8, "Retour");
            retour.ActionHandler = AfficherEditionCompetences;
            vue.addWidget(retour);
            vue.setReady();
            vue.draw();
        }

        public void AfficherEditionCompetences(List<Object> parameters)
        {

            vue.reset();

            LabelWidget TitrePage = new LabelWidget(35, 5, 50, 2, "COMPETENCES " + univers.Nom);
            TitrePage.HasBorder = false;
            vue.addWidget(TitrePage);

            List<Competence> competences = univers.Competences;
            int y = 10;
            if (competences.Count > 0)
            {
                LabelWidget lab = new LabelWidget(35, y - 2, 14, 2, "Compétences :");
                lab.HasBorder = false;
                vue.addWidget(lab);
                MenuWidget choixCompetence = new MenuWidget(35, y, 50, 1);
                choixCompetence.IsCenteredText = false;
                foreach (Competence q in competences)
                {
                    List<Object> p = new List<Object>();
                    p.Add(q);
                    choixCompetence.addOption(q.Nom, this.EtiderCompetence, p);
                    choixCompetence.Height += 1;
                    y++;
                }
                vue.addWidget(choixCompetence);
            }
            MenuWidget menu = new MenuWidget(35, y + 2, 50, 3);
            menu.addOption("Ajouter une compétence", AjouterCompetence, null);
            menu.addOption("Retour", AfficherEditionUnivers, null);
            vue.addWidget(menu);
            vue.setReady();
            vue.draw();
        }

        public void AfficherFormulaireNouveauUnivers(List<Object> parameters)
        {
            vue.reset();
            ButtonWidget retour = new ButtonWidget(0, 0, 20, "Retour");
            retour.ActionHandler = AfficherGestionUnivers;
            vue.addWidget(retour);

            FormWidget formulaireCreationUnivers = new FormWidget(0, 4, "Création d'univers");
            TextBoxWidget nomUnivers = new TextBoxWidget(0, 0, 10, "Nom de l'univers", "univers.nom");

            formulaireCreationUnivers.addWidget(nomUnivers);
            TextareaWidget descriptionUnivers = new TextareaWidget(500, "Description", 50, 10, "univers.description");
            formulaireCreationUnivers.addWidget(descriptionUnivers);
            formulaireCreationUnivers.FormUserMethod = CreerUnivers;
            formulaireCreationUnivers.ForwardAction = AfficherEditionUnivers;
            ButtonWidget validButton = new ButtonWidget(0, 0, 20, "Valider");
            validButton.ActionHandler = formulaireCreationUnivers.endForm;
            formulaireCreationUnivers.addWidget(validButton);
            vue.addWidget(formulaireCreationUnivers);
            vue.setReady();
            vue.draw();
        }

        public void AfficherGestionUnivers(List<Object> parameters)
        {

            vue.reset();
            List<string> univers = ServiceUnivers.listerUnivers();
            int y = 15;
            if (univers.Count > 0)
            {
                LabelWidget lab = new LabelWidget(45, y - 2, 10, 2, "Univers :");
                lab.HasBorder = false;
                vue.addWidget(lab);
                MenuWidget menu = new MenuWidget(45, y, 24, univers.Count+1);
                menu.IsCenteredText = false;
                if (univers.Count > 0)
                {
                    foreach (string s in univers)
                    {
                        List<Object> p = new List<Object>();
                        p.Add(s);
                        menu.addOption("Univers " + s, AfficherEditionUnivers, p);
                        y++;
                    }

                    y = menu.Y + menu.Height + 2;
                }
                vue.addWidget(menu);
            }
            MenuWidget m = new MenuWidget(45, y, 24, 3);
            m.addOption("Nouvel univers", AfficherFormulaireNouveauUnivers, null);
            m.addOption("Retour", play, null);
            vue.addWidget(m);
            vue.setReady();
            vue.draw();
            
        }

        public void AfficherEditionSituations(List<Object> parameters)
        {

            vue.reset();
            LabelWidget titrePage = new LabelWidget(0, 0, 30, 2, "Ecriture de l'histoire");
            vue.addWidget(titrePage);

            PlateauCreatorWidget plateauCreator = new PlateauCreatorWidget(0, 6, 25, 12);
            if (univers.Situations.Count > 0)
            {
                plateauCreator.Plateau.loadSituations(univers.Situations);
            }
            foreach (SituationWidget s in plateauCreator.Plateau.Situations)
            {
                s.IsAccessible = true;
            }
            plateauCreator.CreateSituationAction = AfficherFormulaireCreationSituation;
            vue.addWidget(plateauCreator);
            ButtonWidget retour = new ButtonWidget(31,0 , 20, "Retour");
            retour.ActionHandler = AfficherEditionUnivers;
            vue.addWidget(retour);
            vue.setReady();
            vue.draw();
        }


        public void AfficherFormulaireCreationSituation(List<Object> parameters)
        {

            vue.reset();
            

            SituationWidget sw = null;
            int pos_x=0;
            int pos_y=0;
            string situationtype = "";
            if (parameters != null && parameters.Count == 3)
            {
                sw = parameters[0] as SituationWidget;
                VoidSituationWidget swvide = sw as VoidSituationWidget;
                CombatSituationWidget swcombat = sw as CombatSituationWidget;
                DialogueSituationWidget swdialogue = sw as DialogueSituationWidget;
                EpreuveSituationWidget swepreuve = sw as EpreuveSituationWidget;
                if (swvide != null)
                    situationtype = "vide";
                else if (swcombat != null)
                    situationtype = "combat";
                else if (swdialogue != null)
                    situationtype = "dialogue";
                else if (swepreuve != null)
                    situationtype = "epreuve";

                pos_x = (int)parameters[1];
                pos_y = (int)parameters[2];
            }
            if (situationtype != "")
            {
                Dictionary<string, string> datas = new Dictionary<string, string>();
                datas.Add("situation.position_x", pos_x.ToString());
                datas.Add("situation.position_y", pos_y.ToString());
                FormWidget form = new FormWidget(0,3, "Creation de situation");
                form.Datas = datas;
                form.Datas.Add("situation.type", situationtype);
                if (situationtype == "vide")
                {
                    datas.Add("situation.nom", "");
                    datas.Add("situation.description", "");
                    CreerSituation(datas, AfficherEditionSituations);
                }
                else
                {
                    if (situationtype == "dialogue")
                    {
                        form.Title = "Creation de dialogue";
                        datas.Add("situation.description", "");
                        TextBoxWidget name = new TextBoxWidget(50, "Nom du dialogue", "situation.nom");
                        TextareaWidget message = new TextareaWidget(500, "Message", 50, 5, "dialogue.message");
                        form.addWidget(name);
                        form.addWidget(message);

                        SelectListWidget situationsRequises = new SelectListWidget("Situations requises", "situation.situationsrequises");
                        foreach (Situation s in univers.Situations)
                        {
                            if ((s as SituationVide) == null) // si n'est pas une situation vide
                                situationsRequises.addOption(s.Nom, s.Id);
                        }
                        form.addWidget(situationsRequises);

                    }
                    else if (situationtype == "epreuve")
                    {
                        form.Title = "Creation d'épreuve";
                        TextBoxWidget name = new TextBoxWidget(50, "Nom de l'épreuve", "situation.nom");
                        TextareaWidget description = new TextareaWidget(500, "Description", 50, 10, "situation.description");
                        SliderWidget difficulte = new SliderWidget(0, 0, 50, "epreuve.difficulte", "Difficulté", 0, 10);

                        SelectListWidget selectCompetenceRequise = new SelectListWidget("Compétences requises", "epreuve.competencesrequises");
                        foreach (Competence c in univers.Competences)
                        {
                            selectCompetenceRequise.addOption(c.Nom, c.Id);
                        }

                        SelectListWidget selectCaracteristiquesImpliquees = new SelectListWidget("Caractéristiques impliquées", "epreuve.caracteristiquesimpliquees");
                        foreach (Caracteristique c in univers.Caracteristiques)
                        {
                            selectCaracteristiquesImpliquees.addOption(c.Nom, c.Id);
                        }
                        SelectListWidget situationsRequises = new SelectListWidget("Situations requises", "situation.situationsrequises");
                        foreach (Situation s in univers.Situations)
                        {
                            if((s as SituationVide)==null) // si n'est pas une situation vide
                                situationsRequises.addOption(s.Nom, s.Id);
                        }
                        
                        form.addWidget(name);
                        form.addWidget(description);
                        form.addWidget(difficulte);
                        form.addWidget(selectCompetenceRequise);
                        form.addWidget(selectCaracteristiquesImpliquees);
                        form.addWidget(situationsRequises);
                    }
                    else if (situationtype == "combat")
                    {
                        form.Title = "Personnage à combattre";
                        TextBoxWidget name = new TextBoxWidget(50, "Nom de l'épreuve", "situation.nom");
                        TextareaWidget description = new TextareaWidget(500, "Motif du combat", 50, 10, "situation.description");
                        TextBoxWidget nomCategorie = new TextBoxWidget(50, "Nom du personnage", "combat.categorie.nomCategorie");
                        TextareaWidget descriptionCategorie = new TextareaWidget(500, "Description du personnage", 50, 10, "combat.categorie.description");
                        form.addWidget(name);
                        form.addWidget(description);
                        form.addWidget(nomCategorie);
                        form.addWidget(descriptionCategorie);
                        List<Caracteristique> caracteristiques = univers.Caracteristiques;
                        int y = 15;
                        foreach (Caracteristique q in caracteristiques)
                        {
                            SliderWidget slide = new SliderWidget(0, 0, 50, "combat.caracteristique." + q.Id.ToString(), q.Nom, 0, 10);
                            form.addWidget(slide);
                            y += 2;
                        }
                        SelectListWidget selectCompetenceRequise = new SelectListWidget("Compétences du personnage", "personnage.competences");
                        foreach (Competence c in univers.Competences)
                        {
                            selectCompetenceRequise.addOption(c.Nom, c.Id);
                        }
                        form.addWidget(selectCompetenceRequise);

                        SelectListWidget selectCompetenceCombat = new SelectListWidget("Compétences utilisables pour ce combat", "combat.competences");
                        foreach (Competence c in univers.Competences)
                        {
                            selectCompetenceCombat.addOption(c.Nom, c.Id);
                        }
                        form.addWidget(selectCompetenceCombat);


                        SelectListWidget situationsRequises = new SelectListWidget("Situations requises", "situation.situationsrequises");
                        foreach (Situation s in univers.Situations)
                        {
                          
                                situationsRequises.addOption(s.Nom, s.Id);
                        }
                        form.addWidget(situationsRequises);

                    }
                    else
                    {
                        datas.Add("situation.nom", "");
                        datas.Add("situation.description", "");
                        datas.Add("situation.type", situationtype);
                        CreerSituation(datas, AfficherEditionSituations);
                    }
                    form.FormUserMethod = CreerSituation;
                    form.ForwardAction = AfficherEditionSituations;
                    ButtonWidget validButton = new ButtonWidget(0, 0, 20, "Valider");
                    validButton.ActionHandler = form.endForm;
                    form.addWidget(validButton);
                    ButtonWidget retour = new ButtonWidget(0, 0, 20, "Retour");
                    retour.ActionHandler = AfficherEditionUnivers;
                    vue.addWidget(retour);
                    vue.addWidget(form);
                    vue.setReady();
                    vue.draw();
                }
            }

        }


        public void CreerSituation(Dictionary<string, string> datas, DelegateMethods.ActionForward forward)
        {
            
            univers.ajouterSituation(ServiceSituation.CreerSituation(datas));
            forward(null);
        }
    }
}
