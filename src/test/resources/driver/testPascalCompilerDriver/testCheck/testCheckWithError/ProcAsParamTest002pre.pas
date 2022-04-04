(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 14/01/2022
 *)
{$MODE iso}
{$RANGECHECKS on}
program ProcAsParamTest002pre;
type
  {funcType = function(i:Integer):Integer;
  procType = procedure(var g1:Integer; g2:Integer);}
  placeholder = Integer;
var
  result:Integer ;

{this is allowed in standard pascal}
function sum(procedure p; lower,upper:integer):integer;
var i,j:Integer;
begin
  i:=1;
  p; {works}
  sum:=i; {1}
end;

{this is only allowed in free pascal}
(*function sum(p: procType; lower,upper:integer):integer;
var i,j:Integer;
begin
  i:=1;
  p(i,2);
  p(1,2);{not allowed}
  sum:=p(i,2); {proc returns no value, not allowed}
  sum:=i; {1}
end;*)

procedure addOne;
var sum:Integer;
begin
 sum:= 1+1;
  end;

var
  enumVar:(sunny,cloudy) ;
  flagVar:Boolean;
  {main}
begin
  result:= sum(addOne, 1, 2);
end.
