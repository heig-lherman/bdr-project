import type { components } from '#build/types/nuxt-open-fetch/default';

export type StationSearchViewModel = components['schemas']['StationSearchViewModel'];
export type ConnectingStationViewModel = components['schemas']['ConnectingStationViewModel'];
export type PathfindingViewModel = components['schemas']['PathfindingViewModel'];

export type TrackedSegment = ConnectingStationSegment | PathFindingSegment;

export interface ConnectingStationSegment {
    type: 'connecting-station';
    start: StationSearchViewModel;
    end: ConnectingStationViewModel;
}

export interface PathFindingSegment {
    type: 'path-finding';
    start: StationSearchViewModel;
    end: StationSearchViewModel;
    path: PathfindingViewModel;
}
