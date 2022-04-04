(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 02/01/2022
 *)
{$mode iso}
{$rangeChecks on}
program enumTest005;
type
  cardsuit = (clubs, diamonds, hearts, spades);
var
  cardsuit2 : (clubs2, diamonds2, hearts2, spades2);
begin
  clubs2 := 1; {Illegal assignment [clubs2:=1]. Assigning value to identifieris not allowed}
end.
