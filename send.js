/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var usname;
function getname()
{
    var oTable = document.getElementById('table'); 
    var oCells = oTable.rows.item(0).cells;
    usname = oCells.item(1).innerHTML;
}
function sendotp(){
    var data=[ usname,document.getElementById("rsname").value,
                        document.getElementById("amountgiven").value];
                exe('sendotp',data);
}
function verifyotp(){
    var getotp=document.getElementById("otp").value;
    var originalotp="spark";
    if(originalotp === getotp)
    {
        document.getElementById("able").disabled = false;
        document.getElementById("able2").disabled = false;
        alert("Congrats otp verified");     
    }
}
function transfer(){
    
                var data=[ usname,document.getElementById("rsname").value,
                            document.getElementById("amountgiven").value];
                exe('transfer',data);
            }
            function deposit(){
                var data=[ usname,document.getElementById("rsname").value,
                        document.getElementById("amountgiven").value];
                exe('deposit',data);
            }    
            function transdtl(){
                var data=[ usname,document.getElementById("rsname").value,
                        document.getElementById("amountgiven").value];
                exe('transdtl',data);
            }
            function exe(operation,name){
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status===200) {                
                var data = xhr.responseText;
                if (xhr.responseText.startsWith("<html>")) 
                {
                    document.getElementById("demo").innerHTML=data;
                }
                else
                {
                    alert(data);
                }       
                }      
            };              
            xhr.open('POST', "login?oper="+operation+"&usname="+name[0]+"&rsname="+name[1]+"&amountgiven="+name[2], true);
            xhr.send();
            }