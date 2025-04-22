#### MAP EXPLAINING

## FORMATTING

# Lighting

true/false
true -> default lit up
false -> not lit up, you are forced to have a chandellier to see

MAP WIDTH
MAP HEIGHT

MAP -> The map loading requires the correct amount of values

INTERACTIVEX INTERACTIVEY
INTERACTIVEX INTERACTIVEY
... -> list of interactive tiles, their functionality is determined by their type in the map

EOF

Example of a Map file
false

5 5

01 02 02 02 03
08 00 20 00 04
08 00 -1 00 04
08 00 00 00 04
07 06 06 06 05

2 1

// EOF
This map file generates a 5x5 map with the player in the middle and
a chandellier on top.

# WALLS

01 -> topleft
02 -> top
03 -> topright
04 -> right
05 -> bottomright
06 -> bottom
07 -> bottomleft
08 -> left

# FLOOR

00 -> random floor
10 to 14 -> specific floor

# PLAYER SPAWN

-1 -> player spawn point

# ITEMS

20 -> chandellier
21 -> single candle
22 -> lighters

50 -> door