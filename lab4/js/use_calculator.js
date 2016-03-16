var calculator = new Calculator();
var val="";
var action ="";
var ope ="0";
var pos;


function putChar(c){
	val = val+c;
	affichage(val);
}


function doOperation(op){
	if(action=="")
	{
		if(val!="")
		{
			calculator.add(val);
		}
	}
	else if(action!="")
	{
		operate(action);
	}

	var ope = document.getElementById(op);
	action = ope.getAttribute('data-operande'); 
	affichage(ope.value);
	if(action=="equals")
	{
		var resultat=calculator.equals();
		$("#calc_resultat").val(resultat);
		calculator = new Calculator();		
		action="";
		ope ="0";
	}
	else if(action=="CE")
	{
		reset();
	}
	val=""; 
}

function affichage(ecran)
{
	$("#calc_resultat").fadeOut("500");
	$("#calc_resultat").val(ecran).fadeIn("6000");
}

function operate(ope){
	switch (ope){
		case "add":
			calculator.add(val);
			break;
		case "subtract":
			calculator.subtract(val);
			break;
		case "divide":
			calculator.divide(val);
			break;
		case "multiply":
			calculator.multiply(val);
			break;
		case "cos":
			calculator.cos(val);
			break;
		case "sin":
			calculator.sin(val);
			break;
		case "tan":
			calculator.tan(val);
			break;
		default:
			break;
		}
		action="0";
}

function reset()
{
	calculator = new Calculator();
	var val="";
	var action ="";
	var ope ="0";
	affichage(" ");
}

function GeoLoc()
{
	if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(maPosition);
    } else { 
        affichage("Nous n'avons pas accès à votre position! êtes vous une théière ??");
    }
}

function maPosition(position) {
    pos = "Latitude: " + Math.round(position.coords.latitude) + "     Longitude: " + Math.round(position.coords.longitude);	
    affichage(pos);
}