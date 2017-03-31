package apollo

class CollectionService {
    /**
     * Pagina una lista de acuerdo a una posición de inicio y una cantidad máxima de
     * elementos a mostrar.
     */
    List paginate(List list, int offset, int max) {
        int start, end
        int sizeList = list.size()

        if (offset == null || offset < 0) {
            start = 0
        } else {
            start = offset
        }

        if (start >= sizeList) {
            return []
        }

        if (max == null || max <= 0) {
            end = sizeList - 1
        } else {
            end = start + max - 1

            if (end >= sizeList) {
                end = sizeList - 1
            }
        }

        list[start..end]
    }

    /**
     * Ordena una lista de acuerdo a la fecha de creación de sus elementos. Si asc es
     * 'true' se ordena de manera ascendente.
     */
    List orderByDateCreated(List list, boolean asc) {
        List sortList = list.sort(false) { a, b ->
            a.dateCreated <=> b.dateCreated
        }

        asc ? sortList : sortList.reverse()
    }
}
