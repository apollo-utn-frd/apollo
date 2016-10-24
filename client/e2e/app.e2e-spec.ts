import { ApolloClientPage } from './app.po';

describe('apollo-client App', function() {
  let page: ApolloClientPage;

  beforeEach(() => {
    page = new ApolloClientPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
