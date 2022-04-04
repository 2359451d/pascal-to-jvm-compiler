{$mode iso}
{$RANGECHECKS ON}
program arrayTestB005;

var
  charArrVar1, charArrVar3: array ['a'..'f'] of Integer;
  charArrVar2: array ['a'..'a'] of Integer;

begin
  charArrVar1:=charArrVar3;
  charArrVar1:=charArrVar2;{incompatible array types}
end.
