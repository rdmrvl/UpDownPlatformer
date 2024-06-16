[DATABASE]
- create a new database and import updown.db

[COMPILE VIA CMD]
- open cmd in the project directory
- compile all .java file and include the .jar file for db connection

command:
cd src
javac -cp .;../mysql-connector-java-8.0.25.jar model/GameModel.java model/DB.java view/GameView.java viewmodel/GameViewModel.java MainMenu.java Main.java
cd ..
java -cp src;mysql-connector-java-8.0.25.jar Main


[ASSETS USED]
"Up Down" - PLATFORMER TYPE GAME

- background : https://www.istockphoto.com/id/vektor/taman-bermain-seni-pixel-metropolis-dengan-gedung-pencakar-langit-di-malam-hari-gm1257225648-368375838
- character  : https://lunarcore-games-llc.itch.io/procedural-character-creator?download
- explosion  : https://www.pngegg.com/en/png-eclaj
- obstacle
	up: 	https://www.istockphoto.com/id/vektor/mesin-truk-vektor-pixel-dengan-crane-gm947409792-258690385
	down:	https://www.freepik.com/premium-vector/trashcan-pixel-art_22094051.htm
		https://www.istockphoto.com/id/vektor/seni-piksel-kantong-sampah-karung-hitam-sampah-8-bit-ilustrasi-vektor-gm1160678998-317772827

[SFX & BGM]
- jump : 		https://pixabay.com/id/sound-effects/8-bit-jump-001-171817/
- explosion : 		https://pixabay.com/sound-effects/8-bit-explosion1wav-14656/
- bgm : 		https://www.fesliyanstudios.com/royalty-free-music/download/funny-bit/2399

[INSPIRATIONS]
- fireboy & watergirl [Oslo Albet]
- super mario bros [Nintendo]
- rogue soul [Not Doppler]
