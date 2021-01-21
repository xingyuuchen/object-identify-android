#include "timeutil.h"
#include <chrono>

using namespace std::chrono;

uint64_t GetCurrentTimeMillis() {
    time_point <std::chrono::system_clock, milliseconds> tp =
            time_point_cast<milliseconds>(system_clock::now());

    return tp.time_since_epoch().count();;
}

