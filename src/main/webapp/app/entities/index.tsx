import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Production from './production';
import Category from './category';
import Brand from './brand';
import Guarantee from './guarantee';
import GuaranteeProduction from './guarantee-production';
import Promotion from './promotion';
import PromotionProduction from './promotion-production';
import Cart from './cart';
import CartProduction from './cart-production';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}production`} component={Production} />
      <ErrorBoundaryRoute path={`${match.url}category`} component={Category} />
      <ErrorBoundaryRoute path={`${match.url}brand`} component={Brand} />
      <ErrorBoundaryRoute path={`${match.url}guarantee`} component={Guarantee} />
      <ErrorBoundaryRoute path={`${match.url}guarantee-production`} component={GuaranteeProduction} />
      <ErrorBoundaryRoute path={`${match.url}promotion`} component={Promotion} />
      <ErrorBoundaryRoute path={`${match.url}promotion-production`} component={PromotionProduction} />
      <ErrorBoundaryRoute path={`${match.url}cart`} component={Cart} />
      <ErrorBoundaryRoute path={`${match.url}cart-production`} component={CartProduction} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
