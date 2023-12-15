CALL clone_journey_for_team('loic.herman@heig-vd.ch', 2); -- expect notice: no team
CALL clone_journey_for_team('vicky.butty@heig-vd.ch', 1); -- expect fail: journey not own
CALL clone_journey_for_team('vicky.butty@heig-vd.ch', 2);
