(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 27/02/2022
 *)
program procForwardTest;

Procedure First (n : Integer); forward;
Procedure Second;
begin
  WriteLn ('In second. Calling first...');
  First (1);
end;

{Procedure First (n : Integer);}
Procedure First;
begin
  WriteLn ('First received : ',n);
end;
begin
  Second;
end.
