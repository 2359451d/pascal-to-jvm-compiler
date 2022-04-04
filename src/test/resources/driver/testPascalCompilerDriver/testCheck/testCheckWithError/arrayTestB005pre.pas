{$mode iso}
{$RANGECHECKS ON}
program arrayTestB005pre;

var
  charArrVar1, charArrVar3: array ['a'..'f'] of Integer;
  charArrVar2: array ['a'..'a'] of Integer;

begin
  charArrVar1:=charArrVar3;
end.
