package apollo

class SearchService {
    CollectionService collectionService

    List findAll(Search search) {
        String query = search.query ?: ''

        List<String> tokens = query.split(' ')

        List results = search.classx.createCriteria().list() {
            for (token in tokens) {
                or {
                    for (property in search.properties) {
                        ilike(property, '%' + token + '%')
                    }
                }
            }
        }

        collectionService.paginate(results, search.offset, search.max)
    }
}
