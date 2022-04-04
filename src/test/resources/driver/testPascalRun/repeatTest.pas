(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/6
 *)
program repeatTest(output);
var
  a: Integer;

begin
  a := 10;
  (* repeat until loop execution *)
  repeat
    a := a + 1;
    Writeln(a);
  until a > 13.0;
end.