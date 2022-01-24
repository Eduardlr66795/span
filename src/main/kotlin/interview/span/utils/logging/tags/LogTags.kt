package interview.span.utils.logging.tags

import interview.span.utils.logging.objects.LogTagObject

object LogTags {

    // ------------------------------------------
    // THE FOLLOWING TAGS ARE RESERVED FOR INFO
    // ------------------------------------------

    val CREATE_TEAM_ENTITY = LogTagObject(
        "CreateNewTeamEntry", "NewCreateNewTeamEntityCreated"
    )

    val CREATE_TEAM_STANDING_ENTITY = LogTagObject(
        "CreateTeamStandingEntity", "NewTeamStandingEntityCreated"
    )

    val CREATE_MATCH_ENTITY = LogTagObject(
        "CreateMatchEntity", "NewMatchEntityCreated"
    )

    val CREATE_MATCH_POINT_ALLOCATION_ENTITY = LogTagObject(
        "CreateMatchPointAllocationEntity", "NewMatchPointAllocationEntityCreated"
    )

    val PUBLISH_TEAM_STANDING_RESULT = LogTagObject(
        "TeamStandingResult", "Result"
    )
}
