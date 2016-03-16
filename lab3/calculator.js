function NumberCalculator(num){
	this.result = num;
    boolean deg = true ;

	this.add = function(a){
		if(!isNaN(a)){
			this.result +=a;
			return this;
		}
		else{
    		return ;
		}
	};

    this.substract = function(a){
    	if(!isNaN(a)){
    		this.result -=a;
			return this;
    	}
    	else{
    		return ;
    	}
    };

    this.multiply = function(a){
    	if(!isNaN(a)){
    		this.result *=a;
			return this;
    	}
    	else{
    		return ;
    	}
    };

    this.divide = function(a){
    	if(!isNaN(a)){
            if(a!=0)
            {
                this.result /=a;
            }
            else{
            }
			return this;
    	}
    	else{
    		return ;
    	}
    };

    this.cos = function(){
    	this.result = Math.cos(this.result);
    	return  this;
    };

    this.sin = function(){
    	this.result = Math.sin(this.result);
    	return  this;
    };
    
    this.tan = function(){
    	this.result = Math.tan(this.result);
    	return  this;
    };

    function factorialImpl(a){
    	if(!isNaN(a)){
			if(a==0 || a==1){
				return 1;
			}
			else {
				return a*factorialImpl(a-1);
			}
		}
		else{
			return ;
		}
    };

    this.fact = function(){
    	this.result = factorialImpl(this.result);
    	return  this;
    };

    function convDeg_Rad(x){
        return (x*Math.pi / 180);
    };

    function convRad_Deg(x){
        return (180*x/Math.pi);
    };
}

