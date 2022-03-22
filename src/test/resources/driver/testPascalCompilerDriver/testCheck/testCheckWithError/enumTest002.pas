(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 02/01/2022
 *)
{$mode iso}
{$rangeChecks on}
program enumTest;
var
  cardsuit2 : (clubs2, diamonds2, hearts2, spades2);
begin
  cardsuit2 := clubs2;
  cardsuit2 := clubs3; {Not a valid id in acceptable enum value map}
end.