package apollo

class CollectionService {
    /**
     * Pagina una lista de acuerdo a una posición de inicio y una cantidad máxima de
     * elementos a mostrar.
     */
    List paginate(List list, int offset, int max) {
        if (offset == null || offset < 0) {
            offset = 0
        }

        if (offset >= list.size()) {
            return []
        }

        if (max == null || max <= 0) {
            max = list.size() - offset
        }

        int end = offset + max - 1

        if (end >= list.size()) {
            end = list.size() - 1
        }

        list[offset..end]
    }

    List orderByDateCreated(List list, boolean asc) {
        List orderList = list.sort { a, b ->
            a.dateCreated <=> b.dateCreated
        }

        asc ? orderList : orderList.reverse()
    }
}
