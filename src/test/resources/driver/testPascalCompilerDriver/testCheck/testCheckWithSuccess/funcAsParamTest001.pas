(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 14/01/2022
 *)
{$MODE iso}
{$RANGECHECKS on}
program funcAsParamTest;
type
  placeholder = Integer;
var
  result:Integer ;

{this is allowed in standard pascal}
function sigmaStd(function f(i:integer):Integer; lower,upper:integer): integer;
var i,j,sum:Integer;
begin
  sum := 0;
  for i := lower to upper do sum:=sum+f(i);
  sigmaStd := sum;
end;

{this is only allowed in free pascal, not standard}
{function sigma(f: funcType; lower,upper:integer):integer;
var i,j,sum:Integer;
begin
  sum:=0;
  for j:=lower to upper do sum:= sum + f(j);
  sigma:=sum
end;}

function fourthpower(x: Integer):Integer ;
begin
  fourthpower:= x*x*x*x;
end;

begin
  result:= sigmaStd(fourthpower, 1, 2);
  
end.