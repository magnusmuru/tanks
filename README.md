# Tanks
Lihtne 2D ülaltvaates võrgus elav tankimäng. Eesmärk teise mängija tanki elud nulli viia. 

![Pilt](https://gitlab.cs.ttu.ee/kapala/iti0202-2019-gui/raw/931abc4f1d62421a21a38c4631f1572e33d21648/images/2019-02-18_23-17.png)


### Liikmed
- Kaur Palang (kapala)
- Magnus Muru (magmur)

### Funktsionaalsus
Peamine eesmärk õppida võrgundust. Mäng kasutab _dedicated server_ it, et pidada järge mängijate asukoha ja statistika üle ning edastada mängijate tegevusi teineteisele.

- Tank põõrab parema ja vasaku noolega
- Tank liigub edasi ja tagasi ülemise ja alumise noolega
- Torn jälgib hiire asukohta
- Tulistada saab vasaku hiireklahviga
- Mürsuga pihta saades elud vähenevad

### Tehniline info
Kliendi aken JavaFX-iga. Dependency management Gradleis ja suhtlus serveriga Socketitega.

### Punktid
Funktsionaalsuse eest alla 75p ei lepi.

### Vaated
##### Esileht
- Nimekiri kohtvõrgus leitud serveritest ning nende kõrval nupp ühinemiseks ja number (_ühendatud_ **/** _kõik_)
- Juhised
- Seaded
- Välju mängust

##### Juhised
Kena pilt nuppudest

##### Seaded
Võimalus muuta nuppe

### Plaan

Tähtaeg | Mis tehtud saab
------------- | -------------|
18.02.2019 | Crude playability: Finished controls, mouse tracking and shooting mechanics. **FRAMERATE CAP**
04.03.2019 | Basic networking: Server has basic functionality. Crude packet protocol is in place and clients can communicate with server.
18.03.2019 | Beautiful: Eyecandy aprites and animations, pretty playing fields.K