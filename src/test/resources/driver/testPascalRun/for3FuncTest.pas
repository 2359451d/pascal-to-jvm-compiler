(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 22/02/2022
 *)
program for3FuncTest;
var
  a: integer;

function forFunc: Integer;
var i:Integer;
begin
  for a:=1 to 5 do
  begin
    forFunc := i+a;
  end;
end;

begin
  WriteLn(forFunc());
end.