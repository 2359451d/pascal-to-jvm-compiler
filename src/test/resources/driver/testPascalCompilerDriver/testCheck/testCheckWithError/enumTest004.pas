(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 02/01/2022
 *)
{$mode iso}
{$rangeChecks on}
program enumTest004;
type
  cardsuit = (clubs, diamonds, hearts, spades);
var
  cardsuit2 : (clubs2, diamonds2, hearts2, spades2);
begin
  cardsuit := 1; {Illegal assignment [cardsuit:=1]. Assigning value to identifier is not allowed}
end.
