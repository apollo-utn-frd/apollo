package apollo

class SearchService {
    List findAll(Search search) {
        String query = search.query ?: ''

        List<String> tokens = query.split(' ')

        List<RutaViaje> results = search.classx.createCriteria().list() {
            for (token in tokens) {
                or {
                    for (property in search.properties) {
                        ilike(property, '%' + token + '%')
                    }
                }
            }
        }

        Map queryParams = [cache: true]

        if (search.offset != null) {
            queryParams['offset'] = search.offset
        }

        if (search.max != null) {
            queryParams['max'] = search.max
        }

        results = search.after.call(results)

        search.classx.findAll(
            "from ${search.classx.getSimpleName()} as result where result in (:results)",
            [results: results],
            queryParams
        )
    }
}
