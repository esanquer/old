
var arrayImages= ["http://fc04.deviantart.net/fs7/i/2005/190/4/f/dumb_kitten_by_JayJayz.jpg","https://c1.staticflickr.com/9/8162/7597905940_51b682f89c_z.jpg","http://s3.amazonaws.com/i.jpg.to/l/271","http://placekitten.com/g/300/300"];
var timer = setInterval(goRight,2000);
function initSelector(){

	var selector = document.getElementById("selector");
	for(var i = 0 ; i < arrayImages.length;i++){
		if(i==0){
			selectImage(i);
		}
		var button = document.createElement("BUTTON");
		var t = document.createTextNode(i+1);
		button.appendChild(t);
		button.setAttribute("id","button-"+i);
		button.setAttribute("class","");
		button.onclick= function(){
				var index = this.id.split("-")[1];
				active(index);
				selectImage(index);
			}
		selector.appendChild(button);			
	}
	active(0);
}

function selectImage(id){
	var image = document.getElementById("image");
	image.style.backgroundImage="url("+arrayImages[id]+")";
    image.style.backgroundRepeat="no-repeat";
    image.style.backgroundPosition="center";
    var carousel = document.getElementById("mainCarousel");
    carousel.setAttribute("numImage",id);        
}

function goLeft(){
	var carousel = document.getElementById("mainCarousel");
	var id =  parseInt(carousel.getAttribute("numImage"));
	if(id==0){
		id=arrayImages.length-1;
	}
	else{
		id--;
	}
	selectImage(id);
	active(id);
}

function goRight(){
	var carousel = document.getElementById("mainCarousel");
	var id = parseInt(carousel.getAttribute("numImage"));
	id = (id+1)%arrayImages.length;
	selectImage(id);
	active(id);
}

function active(i){
	desactive();
	var bout = document.getElementById("button-"+i);
	var but = document.getElementById("button-"+i);
  	but.setAttribute("class","actif");
}

function desactive(){
	for(var i = 0 ; i < arrayImages.length;i++){
   		var but = document.getElementById("button-"+i);
   		but.removeAttribute("class");
  	}	
}

