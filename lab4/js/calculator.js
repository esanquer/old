var str = "12/5*9+9.4*2".replace(/[^-()\d/*+.]/g, '');

var f = [];
Math.factorial = function(n) {
    if (n == 0 || n == 1) {
        return 1;
    }
    if (f[n] > 0) {
        return f[n];
    }
    return f[n] = Math.factorial(n-1) * n;
}

// Classe Calculator
// Toutes les méthodes sauf `equals` retournent `this`, ce qui permet de chainer les appels
// Ex: 
// var calculator = new Calculator()
// calculator.add(2).add(3).subtract(2).equals()
// Retourne : 2 
// 2 + 3 - 2 = 2
var Calculator = function () {
    var memory;

    var equation = '';

    // Ajouter seulement une valeur à l'équation
    // Sera utile pour lorsque cette classe sera connectée au UI
    this.value = function(value) {
        if(typeof value !== 'undefined'){
            equation += parseFloat(value);
        }
        return this;
    }

    // Réinitialiser l'équation 
    this.clear = function() {
        equation = '';
        return this;
    }
    
    this.add = function(value) {
        equation += '+';
        if(typeof value !== 'undefined'){
            equation += parseFloat(value);
        }
        return this;
    }

    this.subtract = function (value) {
        equation += '-'
        if(typeof value !== 'undefined'){
            equation += parseFloat(value);
        }
        return this;
    }

    this.multiply = function (value) {
        equation += '*'
        if(typeof value !== 'undefined'){
            equation += parseFloat(value);
        }
        return this;
    }

    this.divide = function (value) {
        equation += '/'
        if(typeof value !== 'undefined'){
            if (value == 0) {
                throw "Division par zéro!!!";
            }
            equation += parseFloat(value);
        }
        return this;
    }

    this.sin = function(value) {
        equation += 'Math.sin(' + parseFloat(value) + ')'
        return this;
    }

    this.cos = function(value) {
        equation += 'Math.cos(' + parseFloat(value) + ')'
        return this;
    }

    this.tan = function() {
        equation += 'Math.tan(' + parseFloat(value) + ')'
        return this;
    }

    this.setMemory = function(memoryValue) {
        memory = memoryValue;
    }

    this.getMemory = function() {
        return memory;
    }

    this.factorial = function(value) {
        if(typeof value === 'undefined'){
            equation = 'Math.factorial(' + this.equals() + ')'
        } else {
            equation += 'Math.factorial(' + parseFloat(value) + ')'
        }
        return this;
    }

    this.equals = function () {
        // Il faut être très prudent avec eval !!! Eval pourrait permettre d'injecter du code malicieux et l'exécuter
        // C'est pourquoi toutes nos variables 'value' sont passées dans 'parseFloat'
        console.log('Evaluating :', equation);
        var equationSolution = eval(equation); 
        equation= ''; 
        return equationSolution;
    }

    this.getEquation = function(){
        return equation;
    }
}